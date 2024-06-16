package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "electronics")
public class Electronic extends Product {

    private String model;

    private String brand;

    private String name;

    private String features;

    private ElectronicType type;

    private Set<ProductAvailabilityElectronic> availability;

}
