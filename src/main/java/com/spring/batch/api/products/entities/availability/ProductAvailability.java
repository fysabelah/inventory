package com.spring.batch.api.products.entities.availability;

import com.spring.batch.api.products.entities.Dimensions;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductAvailability {

    private String sku;

    private Integer quantity;

    private Integer protection;

    private LocalDateTime updatedAt;

    private Dimensions dimensions;

    private Integer reservedQuantity;
}
