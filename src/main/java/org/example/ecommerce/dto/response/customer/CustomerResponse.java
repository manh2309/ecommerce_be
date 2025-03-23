package org.example.ecommerce.dto.response.customer;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;

    private Long accountId;

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String address;

    private String phone;

    private Boolean isActive;
}
