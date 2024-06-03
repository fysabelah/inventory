package com.spring.batch.api.products.entities;

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

    private String size;

    private String name;

    private String color;
}
