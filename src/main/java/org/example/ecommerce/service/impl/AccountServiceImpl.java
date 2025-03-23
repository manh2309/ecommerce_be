package org.example.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.request.account.AccountRequest;
import org.example.ecommerce.dto.request.account.UpdateAcountRequest;
import org.example.ecommerce.dto.response.account.AccountResponse;
import org.example.ecommerce.entity.Account;
import org.example.ecommerce.entity.AccountRole;
import org.example.ecommerce.entity.Role;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.AccountRepository;
import org.example.ecommerce.repository.AccountRoleRepository;
import org.example.ecommerce.repository.RoleRepository;
import org.example.ecommerce.service.AccountService;
import org.example.ecommerce.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final CustomerService customerService;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        if(accountRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "username already exist");
        }

        if(accountRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "email already exist");
        }

        Account account = Account.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .isActive(true)
                .build();

        Account saveAccount = accountRepository.save(account);

        List<Role> roles = roleRepository.findAllById(request.getRoleIds());
        if(roles.size() != request.getRoleIds().size()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "One or more roles are invalid!");
        }

        List<AccountRole> accountRoles = roles.stream().map(role -> AccountRole.builder()
                .accountId(saveAccount.getId())
                .roleId(role.getId())
                .build()).collect(Collectors.toList());

        accountRoleRepository.saveAll(accountRoles);

        if(customerService.checkCustomerExist(saveAccount.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "customer already exist");
        }
        customerService.createCustomer(saveAccount.getId());

        return AccountResponse.builder()
                .id(saveAccount.getId())
                .username(saveAccount.getUsername())
                .email(saveAccount.getEmail())
                .isActive(saveAccount.getIsActive())
                .roleNames(roles.stream().map(Role::getName).toList())
                .build();
    }

    @Override
    public Page<AccountResponse> getListAccounts(String username, String email, List<Long> roleIds, Pageable pageable) {
        Page<Object[]> rawResults = accountRepository.searchAccountsWithRoles(username, email, roleIds, pageable);

        Map<Long, AccountResponse> accountMap = new LinkedHashMap<>();
        rawResults.getContent().forEach(raw -> {
            Long id = (Long) raw[0];
            String usernames = (String) raw[1];
            String emails = (String) raw[2];
            Boolean active = (Boolean) raw[3];
            String roleName = (String) raw[4];

            AccountResponse accountResponse = accountMap.get(id);

            if(accountResponse == null) {
                accountResponse = AccountResponse.builder()
                        .id(id)
                        .username(usernames)
                        .email(emails)
                        .isActive(active)
                        .roleNames(new ArrayList<>()) // danh sách role name
                        .build();
                accountMap.put(id, accountResponse);
            }

            if(roleName != null){
                accountResponse.getRoleNames().add(roleName);
            }
        });

        List<AccountResponse> accounts = new ArrayList<>(accountMap.values());


        return new PageImpl<>(accounts, pageable, rawResults.getTotalElements());
    }

    @Override
    public AccountResponse getAccountDetail(Long id) {
        List<Object[]> results = accountRepository.getAccounts(id);
        if(results.isEmpty()){
            throw new ApiException(HttpStatus.NOT_FOUND, "Account not found");
        }

        Object[] firstRow = results.get(0);
        Long accountId = (Long) firstRow[0];
        String username = (String) firstRow[1];
        String email = (String) firstRow[2];
        Boolean active = (Boolean) firstRow[3];

        List<String> roleNames = results.stream().map(role -> (String) firstRow[4]).toList();

        return AccountResponse.builder()
                .id(accountId)
                .username(username)
                .email(email)
                .isActive(active)
                .roleNames(roleNames)
                .build();
    }

    @Override
    public void deleteAccount(Long id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Account not found"));
        existingAccount.setIsActive(false);
        accountRepository.save(existingAccount);
    }
}
