package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Clothes {

    private String model;

    private String brand;

    private String color;

    private String name;

    private Set<ProductAvailabilityClothes> availability;
}
