package org.example.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.service.AccountService;
import org.example.ecommerce.service.AuthenticationService;
import org.example.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService accountService;

    @PostMapping
    public ApiResponse<?> authenticate(@RequestBody LoginRequest loginRequest) throws Exception{
        return ApiResponse.successOf(HttpStatus.OK, "Login thanh cong",accountService.login(loginRequest));
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest registerRequest) throws Exception{
        return ApiResponse.successOf(HttpStatus.CREATED, "Tao account thanh cong", accountService.register(registerRequest));
    }
}
