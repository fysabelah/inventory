package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ElectronicAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ElectronicDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ElectronicPresenter implements PresenterProduct<Electronic> {

    private final ElectronicAvailabilityPresenter availabilityPresenter;

    public ElectronicPresenter(ElectronicAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    @Override
    public ProductDto convert(Electronic entity) {
        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setCategory(ProductCategory.ELECTRONICS);
        productDto.setActive(entity.isActive());
        productDto.setDescription(entity.getDescription());
        productDto.setValue(productDto.getValue());

        ElectronicDto electronicDto = ElectronicDto.builder()
                .model(entity.getModel())
                .brand(entity.getBrand())
                .name(entity.getName())
                .features(entity.getFeatures())
                .type(entity.getType())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();

        productDto.setCategoryInformation(electronicDto);

        return productDto;
    }

    @Override
    public Electronic convert(ProductDto dto) {
        ElectronicDto electronicDto = (ElectronicDto) dto.getCategoryInformation();

        Electronic electronic = Electronic.builder()
                .model(electronicDto.getModel())
                .brand(electronicDto.getBrand())
                .name(electronicDto.getName())
                .features(electronicDto.getFeatures())
                .type(electronicDto.getType())
                .availability(availabilityPresenter.convert(electronicDto.getAvailability()))
                .build();

        electronic.setId(dto.getId());
        electronic.setDescription(dto.getDescription());
        electronic.setValue(dto.getValue());
        electronic.setActive(dto.isActive());

        return electronic;
    }
}
