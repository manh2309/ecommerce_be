package org.example.ecommerce.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password == null || password.isBlank()) {
            buildContraintValidation(constraintValidatorContext, "Password cannot be blank");
            return false;
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            buildContraintValidation(constraintValidatorContext, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt (@$!%*?&)");
            return false;
        }
        return true;
    }

    private void buildContraintValidation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
