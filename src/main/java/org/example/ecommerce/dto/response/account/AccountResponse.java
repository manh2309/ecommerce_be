package org.example.ecommerce.dto.response.account;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;

    private String username;

    private String email;

    private boolean isActive;

    private List<String> roleNames;
}
