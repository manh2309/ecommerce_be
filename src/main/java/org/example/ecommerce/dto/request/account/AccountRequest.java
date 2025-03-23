package org.example.ecommerce.dto.request.account;

import lombok.*;
import org.example.ecommerce.common.validator.ValidPassword;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private String username;
    @ValidPassword
    private String password;

    private String email;

    private List<Long> roleIds;
}
