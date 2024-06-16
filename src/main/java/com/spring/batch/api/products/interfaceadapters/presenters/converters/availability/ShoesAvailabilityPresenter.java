package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ShoeDto;
import org.springframework.stereotype.Component;

@Component
public class ShoesAvailabilityPresenter implements PresenterAvailability<ProductAvailabilityShoe, ShoeDto.ShoeAvailabilityDto> {

    @Override
    public ShoeDto.ShoeAvailabilityDto convert(ProductAvailabilityShoe entity) {
        return ShoeDto.ShoeAvailabilityDto.shoes()
                .sku(entity.getSku())
                .quantity(entity.getQuantity())
                .protection(entity.getProtection())
                .width(entity.getDimensions().getWidth())
                .length(entity.getDimensions().getLength())
                .height(entity.getDimensions().getHeight())
                .size(entity.getSize())
                .build();
    }

    @Override
    public ProductAvailabilityShoe convert(ShoeDto.ShoeAvailabilityDto dto) {
        return ProductAvailabilityShoe.shoeAvailability()
                .sku(dto.getSku())
                .quantity(dto.getQuantity())
                .protection(dto.getProtection())
                .dimensions(Dimensions.builder()
                        .length(dto.getLength())
                        .height(dto.getHeight())
                        .width(dto.getWidth())
                        .build())
                .size(dto.getSize())
                .build();
    }
}
