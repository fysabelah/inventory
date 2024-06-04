package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Shoe {

    private String brand;

    private String name;

    private String color;

    private List<ProductAvailabilityShoe> availability;
}
