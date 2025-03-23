package org.example.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.common.dto.ApiResponse;
import org.example.ecommerce.dto.LoginRequest;
import org.example.ecommerce.dto.RegisterRequest;
import org.example.ecommerce.dto.request.auth.ResetPasswordRequest;
import org.example.ecommerce.entity.Account;
import org.example.ecommerce.entity.PasswordResetToken;
import org.example.ecommerce.exception.ApiException;
import org.example.ecommerce.repository.AccountRepository;
import org.example.ecommerce.repository.PasswordResetTokenRepository;
import org.example.ecommerce.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public ApiResponse<Object> login(LoginRequest loginRequest) {
        Account account = accountRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST,"account khong ton tai"));
        if(!account.getPassword().equals(loginRequest.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"Sai password");
        }

        return ApiResponse.builder()
                .statusCode(200)
                .message("SUCCESS")
                .data(null)
                .build();
    }

    @Override
    public ApiResponse<Object> register(RegisterRequest registerRequest) {
        if(accountRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
           throw new ApiException(HttpStatus.BAD_REQUEST, "account da ton tai");
        }
        Account account = Account.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .build();
        accountRepository.save(account);

        return ApiResponse.builder()
                .statusCode(201)
                .message("CREATED")
                .data(null)
                .build();
    }

    @Override
    @Transactional
    public String forgotPassword(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Email không tồn tại"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .accountId(account.getId())
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(3))
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);
        // TODO: Gửi email token (hoặc link reset) cho user
        String resetLink = "https://your-domain.com/reset-password?token=" + token;
//        emailService.sendEmail(account.getEmail(), "Reset mật khẩu", "Click vào link sau để reset mật khẩu: " + resetLink);
        return token;
    }

    @Override
    @Transactional
    public void resetPassword(String token, ResetPasswordRequest resetPasswordRequest) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Token không hợp lệ"));

        if (resetToken.isUsed()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token đã được sử dụng");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token đã hết hạn");
        }

        if (!resetPasswordRequest.getConfirmPassword().equals(resetPasswordRequest.getNewPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Password không khớp");
        }

        Account account = accountRepository.findById(resetToken.getAccountId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));

        // Cập nhật mật khẩu mới
        account.setPassword(resetPasswordRequest.getNewPassword());
        accountRepository.save(account);

        // Đánh dấu token đã dùng để tránh reuse
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }
}
