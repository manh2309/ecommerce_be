package org.example.ecommerce.dto.request.account;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAcountRequest {
    private String username;
    private String email;
    private List<Long> roleIds;
}
