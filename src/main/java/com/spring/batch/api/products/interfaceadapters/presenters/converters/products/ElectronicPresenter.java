package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ElectronicAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ElectronicDto;
import org.springframework.stereotype.Component;

@Component
class ElectronicPresenter {

    private final ElectronicAvailabilityPresenter availabilityPresenter;

    public ElectronicPresenter(ElectronicAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    public ElectronicDto convert(Electronic entity) {
        return ElectronicDto.builder()
                .model(entity.getModel())
                .brand(entity.getBrand())
                .name(entity.getName())
                .features(entity.getFeatures())
                .type(entity.getType())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();
    }

    public Electronic convert(ElectronicDto electronicDto) {
        return Electronic.builder()
                .model(electronicDto.getModel())
                .brand(electronicDto.getBrand())
                .name(electronicDto.getName())
                .features(electronicDto.getFeatures())
                .type(electronicDto.getType())
                .availability(availabilityPresenter.convert(electronicDto.getAvailability()))
                .build();
    }
}
