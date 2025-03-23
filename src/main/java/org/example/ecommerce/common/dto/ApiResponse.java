package org.example.ecommerce.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private int statusCode;
    private Object message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(HttpStatus status,T data, String message) {
        return ApiResponse.<T>builder()
                .statusCode(status.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, T data, String message) {
        return ApiResponse.<T>builder()
                .statusCode(status.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> successOf(HttpStatus status, String message,  final T response) {
        return ApiResponse.<T>builder()
                .statusCode(status.value())
                .message(message)
                .data(response)
                .build();
    }
}
