package com.spring.batch.api.products.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAvailability {

    private Integer quantity;

    private Integer protection;

    private LocalDateTime updatedAt;

    public ProductAvailability(Integer quantity, Integer protection) {
        this.quantity = quantity;
        this.protection = protection;
    }
}
