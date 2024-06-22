package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.AvailabilityDto;
import org.springframework.stereotype.Component;

@Component
public class ProductAvailabilityPresenter implements PresenterAvailability<ProductAvailability, AvailabilityDto> {

    @Override
    public AvailabilityDto convert(ProductAvailability entity) {
        return AvailabilityDto.builder()
                .quantity(entity.getQuantity())
                .sku(entity.getSku())
                .protection(entity.getProtection())
                .width(entity.getDimensions().getWidth())
                .length(entity.getDimensions().getLength())
                .height(entity.getDimensions().getHeight())
                .reservedQuantity(entity.getReservedQuantity())
                .build();
    }

    @Override
    public ProductAvailability convert(AvailabilityDto dto) {
        return ProductAvailability.builder()
                .sku(dto.getSku())
                .quantity(dto.getQuantity())
                .protection(dto.getProtection())
                .dimensions(
                        Dimensions.builder()
                                .width(dto.getWidth())
                                .height(dto.getHeight())
                                .length(dto.getLength())
                                .build()
                )
                .reservedQuantity(dto.getReservedQuantity())
                .build();
    }
}
