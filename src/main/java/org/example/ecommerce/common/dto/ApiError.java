package org.example.ecommerce.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
