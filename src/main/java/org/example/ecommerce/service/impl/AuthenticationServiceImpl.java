package org.example.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.entity.Account;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.AccountRepository;
import org.example.ecommerce.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    @Override
    public Account login(LoginRequest loginRequest) {
        Account account = accountRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST,"account khong ton tai"));
        if(!account.getPassword().equals(loginRequest.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"Sai password");
        }

        return account;
    }

    @Override
    public Account register(RegisterRequest registerRequest) {
        if(!accountRepository.findByUsername(registerRequest.getUsername()).isEmpty()) {
           throw  new ApiException(HttpStatus.BAD_REQUEST, "account da ton tai");
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(registerRequest.getPassword());
        return accountRepository.save(account);
    }
}
