package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "clothes")
public class Clothes extends Product {

    private String model;

    private String brand;

    private String color;

    private String name;

    private Set<ProductAvailabilityClothes> availability;
}
