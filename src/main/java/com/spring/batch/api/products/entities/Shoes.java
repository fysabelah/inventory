package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "shoes")
public class Shoes extends Product {

    private String brand;

    private String name;

    private String color;

    private Set<ProductAvailabilityShoe> availability;
}
