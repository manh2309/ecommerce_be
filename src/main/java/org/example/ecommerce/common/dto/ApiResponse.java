package org.example.ecommerce.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static final ApiResponse<Void> SUCCESS = ApiResponse.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message(HttpStatus.OK.getReasonPhrase())
            .build();

    public static <T> ApiResponse<T> successOf(HttpStatus status, String message,  final T response) {
        return ApiResponse.<T>builder()
                .statusCode(status.value())
                .message(message)
                .data(response)
                .build();
    }
}
