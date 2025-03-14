package org.example.ecommerce.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private Long brandId;
}
