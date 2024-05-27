package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clothes {

    private String model;

    private String brand;

    private ProductSize size;

    private String color;

    private String name;
}
