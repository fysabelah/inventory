package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ClothesDto;
import org.springframework.stereotype.Component;

@Component
public class ClothesAvailabilityPresenter implements PresenterAvailability<ProductAvailabilityClothes, ClothesDto.ClothesAvailabilityDto> {

    @Override
    public ClothesDto.ClothesAvailabilityDto convert(ProductAvailabilityClothes entity) {
        return ClothesDto.ClothesAvailabilityDto.clothes()
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
    public ProductAvailabilityClothes convert(ClothesDto.ClothesAvailabilityDto dto) {
        return ProductAvailabilityClothes.clothesAvailability()
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
