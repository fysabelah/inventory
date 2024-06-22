package com.spring.batch.api.products.entities.availability;

import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.utils.enums.ProductSize;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ProductAvailabilityClothes extends ProductAvailability {

    private ProductSize size;

    @Builder(builderMethodName = "clothesAvailability")
    public ProductAvailabilityClothes(String sku, Integer quantity, Integer protection, LocalDateTime updatedAt,
                                      Dimensions dimensions, ProductSize size, Integer reservedQuantity) {
        super(sku, quantity, protection, updatedAt, dimensions, reservedQuantity);
        this.size = size;
    }
}
