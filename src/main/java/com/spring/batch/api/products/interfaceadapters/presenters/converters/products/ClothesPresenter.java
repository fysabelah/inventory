package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Clothes;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ClothesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ClothesDto;
import org.springframework.stereotype.Component;

@Component
class ClothesPresenter {

    private final ClothesAvailabilityPresenter availabilityPresenter;

    public ClothesPresenter(ClothesAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    public ClothesDto convert(Clothes entity) {
        return ClothesDto.builder()
                .model(entity.getModel())
                .brand(entity.getBrand())
                .color(entity.getColor())
                .name(entity.getName())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();
    }

    public Clothes convert(ClothesDto clothesDto) {
        return Clothes.builder()
                .model(clothesDto.getModel())
                .name(clothesDto.getName())
                .color(clothesDto.getColor())
                .brand(clothesDto.getBrand())
                .availability(availabilityPresenter.convert(clothesDto.getAvailability()))
                .build();
    }
}
