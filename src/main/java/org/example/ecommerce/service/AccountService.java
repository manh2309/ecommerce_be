package org.example.ecommerce.service;

import org.example.ecommerce.dto.request.account.AccountRequest;
import org.example.ecommerce.dto.request.account.UpdateAcountRequest;
import org.example.ecommerce.dto.response.account.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest account);
    Page<AccountResponse> getListAccounts(String username, String email, List<Long> roleIds, Pageable pageable);
    AccountResponse getAccountDetail(Long id);
    void deleteAccount(Long id);
}
