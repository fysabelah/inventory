package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ElectronicDto;
import org.springframework.stereotype.Component;

@Component
public class ElectronicAvailabilityPresenter implements PresenterAvailability<ProductAvailabilityElectronic, ElectronicDto.ElectronicAvailabilityDto> {

    @Override
    public ElectronicDto.ElectronicAvailabilityDto convert(ProductAvailabilityElectronic entity) {
        return ElectronicDto.ElectronicAvailabilityDto.electronic()
                .sku(entity.getSku())
                .quantity(entity.getQuantity())
                .protection(entity.getProtection())
                .width(entity.getDimensions().getWidth())
                .length(entity.getDimensions().getLength())
                .height(entity.getDimensions().getHeight())
                .color(entity.getColor())
                .reservedQuantity(entity.getReservedQuantity())
                .build();
    }

    @Override
    public ProductAvailabilityElectronic convert(ElectronicDto.ElectronicAvailabilityDto dto) {
        return ProductAvailabilityElectronic.electronicAvailability()
                .color(dto.getColor())
                .sku(dto.getSku())
                .quantity(dto.getQuantity())
                .protection(dto.getProtection())
                .dimensions(
                        Dimensions.builder()
                                .length(dto.getLength())
                                .width(dto.getWidth())
                                .height(dto.getHeight())
                                .build()
                )
                .reservedQuantity(dto.getReservedQuantity())
                .build();
    }
}
