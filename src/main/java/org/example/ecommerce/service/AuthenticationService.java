package org.example.ecommerce.service;

import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.entity.Account;

public interface AuthenticationService {
    Account login(LoginRequest loginRequest);
    Account register(RegisterRequest registerRequest);
}
