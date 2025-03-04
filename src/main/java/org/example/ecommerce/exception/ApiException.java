package org.example.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String message;

    public ApiException(final HttpStatus statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}
