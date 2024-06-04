package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Electronic {

    private String model;

    private String brand;

    private String name;

    private String features;

    private ElectronicType type;

    private List<ProductAvailabilityElectronic> availability;

}
