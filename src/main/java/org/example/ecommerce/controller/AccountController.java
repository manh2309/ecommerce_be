package org.example.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.request.account.AccountRequest;
import org.example.ecommerce.dto.request.account.UpdateAcountRequest;
import org.example.ecommerce.dto.response.account.AccountResponse;
import org.example.ecommerce.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ApiResponse<Object> createAccount(@Valid @RequestBody AccountRequest request) {
        return ApiResponse.success(HttpStatus.CREATED, accountService.createAccount(request), "Account created");
    }

    @GetMapping("/list")
    public ApiResponse<Object> listAccounts(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) List<Long> roleIds,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<AccountResponse> page = accountService.getListAccounts(username, email, roleIds, pageable);
        return ApiResponse.success(HttpStatus.OK, page, "Account list");
    }

    @GetMapping("/{id}")
    public ApiResponse<Object> getAccount(@PathVariable Long id) {
        return ApiResponse.success(HttpStatus.OK, accountService.getAccountDetail(id), "Account detail");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Object> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ApiResponse.success(HttpStatus.NO_CONTENT, null, "Account deleted");
    }
}
