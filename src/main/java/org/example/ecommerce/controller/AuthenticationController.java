package org.example.ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.dto.request.auth.ForgotPasswordRequest;
import org.example.ecommerce.dto.request.auth.ResetPasswordRequest;
import org.example.ecommerce.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public ApiResponse<Object> authenticate(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.success( HttpStatus.OK, authenticationService.login(loginRequest) ,"Login thanh cong");
    }

    @PostMapping("/register")
    public ApiResponse<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ApiResponse.success(HttpStatus.CREATED, authenticationService.register(registerRequest), "Tao account thanh cong");
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Object> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        return ApiResponse.success(HttpStatus.NO_CONTENT,authenticationService.forgotPassword(request.getEmail()),"Đã gửi link reset password tới email nếu tồn tại");
    }

    @PostMapping("/reset-password")
    public ApiResponse<Object> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request.getToken(), request);
        return ApiResponse.success(HttpStatus.OK,null,"Đổi mật khẩu thành công");
    }
}
