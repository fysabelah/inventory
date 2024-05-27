package com.spring.batch.api.products.entities;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shoe {

    private String brand;

    @Size(min = 1, max = 2)
    private String size;

    private String name;

    private String color;
}
