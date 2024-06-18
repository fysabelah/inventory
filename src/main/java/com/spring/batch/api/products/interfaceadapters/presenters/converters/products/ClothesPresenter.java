package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Clothes;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ClothesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ClothesDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ClothesPresenter implements PresenterProduct<Clothes> {

    private final ClothesAvailabilityPresenter availabilityPresenter;

    public ClothesPresenter(ClothesAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    @Override
    public ProductDto convert(Clothes entity) {
        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setCategory(ProductCategory.CLOTHES);
        productDto.setActive(entity.isActive());
        productDto.setDescription(entity.getDescription());
        productDto.setValue(entity.getValue());

        ClothesDto clothesDto = ClothesDto.builder()
                .model(entity.getModel())
                .brand(entity.getBrand())
                .color(entity.getColor())
                .name(entity.getName())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();

        productDto.setCategoryInformation(clothesDto);

        return productDto;
    }

    @Override
    public Clothes convert(ProductDto dto) {
        ClothesDto clothesDto = (ClothesDto) dto.getCategoryInformation();

        Clothes clothes = Clothes.builder().model(clothesDto.getModel()).name(clothesDto.getName()).color(clothesDto.getColor()).brand(clothesDto.getBrand()).availability(availabilityPresenter.convert(clothesDto.getAvailability())).build();

        clothes.setId(dto.getId());
        clothes.setDescription(dto.getDescription());
        clothes.setValue(dto.getValue());
        clothes.setActive(dto.isActive());

        return clothes;
    }
}
