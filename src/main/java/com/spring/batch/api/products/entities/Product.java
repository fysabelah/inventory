package com.spring.batch.api.products.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    private String id;

    private String description;

    private BigDecimal value;

    private boolean active;

    @Builder(builderMethodName = "product")
    public Product(String description, BigDecimal value, boolean active) {
        this.description = description;
        this.value = value;
        this.active = active;
    }
}
