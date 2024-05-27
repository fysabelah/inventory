package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.EletronicType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eletronic {

    private String model;

    private String brand;

    private String color;

    private String name;

    private String features;

    private EletronicType type;

}
