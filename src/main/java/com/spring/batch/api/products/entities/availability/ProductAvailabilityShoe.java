package com.spring.batch.api.products.entities.availability;

import com.spring.batch.api.products.entities.Dimensions;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ProductAvailabilityShoe extends ProductAvailability {

    private String size;

    @Builder(builderMethodName = "shoeAvailability")
    public ProductAvailabilityShoe(String sku, Integer quantity, Integer protection, LocalDateTime updatedAt,
                                   Dimensions dimensions, String size, Integer reservedQuantity) {
        super(sku, quantity, protection, updatedAt, dimensions, reservedQuantity);
        this.size = size;
    }
}
