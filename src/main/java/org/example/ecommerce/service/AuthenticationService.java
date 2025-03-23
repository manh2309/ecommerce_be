package org.example.ecommerce.service;

import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.dto.request.auth.ResetPasswordRequest;

public interface AuthenticationService {
    ApiResponse<Object> login(LoginRequest loginRequest);
    ApiResponse<Object> register(RegisterRequest registerRequest);
    String forgotPassword(String email);
    void  resetPassword(String token, ResetPasswordRequest resetPasswordRequest);
}
