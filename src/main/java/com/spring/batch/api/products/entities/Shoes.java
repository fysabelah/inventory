package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Shoes {

    private String brand;

    private String name;

    private String color;

    private Set<ProductAvailabilityShoe> availability;
}
