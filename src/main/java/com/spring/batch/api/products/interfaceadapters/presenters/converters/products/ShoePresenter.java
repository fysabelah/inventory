package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Shoes;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ShoesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ShoeDto;
import org.springframework.stereotype.Component;

@Component
class ShoePresenter {

    private final ShoesAvailabilityPresenter availabilityPresenter;

    public ShoePresenter(ShoesAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    public ShoeDto convert(Shoes entity) {
        return ShoeDto.builder()
                .brand(entity.getBrand())
                .name(entity.getName())
                .color(entity.getColor())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();
    }

    public Shoes convert(ShoeDto shoeDto) {
        return Shoes.builder()
                .brand(shoeDto.getBrand())
                .name(shoeDto.getName())
                .color(shoeDto.getColor())
                .availability(availabilityPresenter.convert(shoeDto.getAvailability()))
                .build();
    }
}
