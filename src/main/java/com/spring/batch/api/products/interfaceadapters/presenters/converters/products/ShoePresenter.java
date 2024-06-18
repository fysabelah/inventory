package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Shoes;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ShoesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ShoeDto;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ShoePresenter implements PresenterProduct<Shoes> {

    private final ShoesAvailabilityPresenter availabilityPresenter;

    public ShoePresenter(ShoesAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    @Override
    public ProductDto convert(Shoes entity) {
        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setCategory(ProductCategory.SHOES);
        productDto.setActive(entity.isActive());
        productDto.setDescription(entity.getDescription());
        productDto.setValue(entity.getValue());

        ShoeDto shoeDto = ShoeDto.builder()
                .brand(entity.getBrand())
                .name(entity.getName())
                .color(entity.getColor())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();

        productDto.setCategoryInformation(shoeDto);

        return productDto;
    }

    @Override
    public Shoes convert(ProductDto dto) {
        ShoeDto shoeDto = (ShoeDto) dto.getCategoryInformation();

        Shoes shoes = Shoes.builder()
                .brand(shoeDto.getBrand())
                .name(shoeDto.getName())
                .color(shoeDto.getColor())
                .availability(availabilityPresenter.convert(shoeDto.getAvailability()))
                .build();

        shoes.setId(dto.getId());
        shoes.setDescription(dto.getDescription());
        shoes.setValue(dto.getValue());
        shoes.setActive(dto.isActive());

        return shoes;
    }
}
