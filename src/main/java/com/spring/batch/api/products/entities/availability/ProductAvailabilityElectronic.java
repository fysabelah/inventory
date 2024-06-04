package com.spring.batch.api.products.entities.availability;

import com.spring.batch.api.products.entities.Dimensions;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ProductAvailabilityElectronic extends ProductAvailability {

    private String color;

    @Builder(builderMethodName = "electronicAvailability")
    public ProductAvailabilityElectronic(String sku, Integer quantity, Integer protection, LocalDateTime updatedAt, Dimensions dimensions, String color) {
        super(sku, quantity, protection, updatedAt, dimensions);
        this.color = color;
    }
}
