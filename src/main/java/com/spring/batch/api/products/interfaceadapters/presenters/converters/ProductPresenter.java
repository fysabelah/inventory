package com.spring.batch.api.products.interfaceadapters.presenters.converters;

import com.spring.batch.api.products.entities.*;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.*;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPresenter implements Presenter<Product, ProductDto> {

    @Override
    public ProductDto convert(Product entity) {
        return ProductDto.builder()
                .product(entity)
                .build();
    }

    @Override
    public Product convert(ProductDto dto) {
        if (ProductCategory.ELECTRONICS.equals(dto.getCategory())) {
            return convertElectronic(dto);
        }

        if (ProductCategory.CLOTHES.equals(dto.getCategory())) {
            return convertClothes(dto);
        }

        if (ProductCategory.BOOKS.equals(dto.getCategory())) {
            return convertBooks(dto);
        }

        return convertShoes(dto);
    }

    private Product convertShoes(ProductDto dto) {
        ShoeDto shoeDto = (ShoeDto) dto.getCategoryInformation();

        return Product.shoe()
                .description(dto.getDescription())
                .value(dto.getValue())
                .active(dto.isActive())
                .shoe(Shoe.builder()
                        .brand(shoeDto.getBrand())
                        .name(shoeDto.getName())
                        .color(shoeDto.getColor())
                        .availability(convertShoes(shoeDto.getAvailability()))
                        .build())
                .build();
    }

    private List<ProductAvailabilityShoe> convertShoes(List<ShoeDto.ShoeAvailabilityDto> availability) {
        return availability.stream()
                .map(item -> ProductAvailabilityShoe.shoeAvailability()
                        .sku(item.getSku())
                        .quantity(item.getQuantity())
                        .protection(item.getProtection())
                        .dimensions(Dimensions.builder()
                                .length(item.getLength())
                                .height(item.getHeight())
                                .width(item.getWidth())
                                .build())
                        .size(item.getSize())
                        .build())
                .collect(Collectors.toList());
    }

    private Product convertClothes(ProductDto dto) {
        ClothesDto clothesDto = (ClothesDto) dto.getCategoryInformation();

        return Product.clothes()
                .description(dto.getDescription())
                .active(dto.isActive())
                .value(dto.getValue())
                .clothes(Clothes.builder()
                        .model(clothesDto.getModel())
                        .name(clothesDto.getName())
                        .color(clothesDto.getColor())
                        .brand(clothesDto.getBrand())
                        .availability(convertClothes(clothesDto.getAvailability()))
                        .build())
                .build();
    }

    public List<ProductAvailabilityClothes> convertClothes(List<ClothesDto.ClothesAvailabilityDto> availability) {
        return availability.stream()
                .map(item -> ProductAvailabilityClothes.clothesAvailability()
                        .sku(item.getSku())
                        .quantity(item.getQuantity())
                        .protection(item.getProtection())
                        .dimensions(Dimensions.builder()
                                .length(item.getLength())
                                .height(item.getHeight())
                                .width(item.getWidth())
                                .build())
                        .size(item.getSize())
                        .build())
                .collect(Collectors.toList());
    }

    private Product convertBooks(ProductDto dto) {
        BookDto bookDto = (BookDto) dto.getCategoryInformation();

        return Product.book()
                .description(dto.getDescription())
                .value(dto.getValue())
                .active(dto.isActive())
                .book(Book.builder()
                        .isbn(bookDto.getIsbn())
                        .title(bookDto.getTitle())
                        .genre(bookDto.getGenre())
                        .pages(bookDto.getPages())
                        .publisher(bookDto.getPublisher())
                        .availability(convertBook(bookDto.getAvailability()))
                        .build()
                )
                .build();
    }

    private Product convertElectronic(ProductDto dto) {
        ElectronicDto electronicDto = ((ElectronicDto) dto.getCategoryInformation());

        return Product.electronic()
                .description(dto.getDescription())
                .value(dto.getValue())
                .active(dto.isActive())
                .electronic(Electronic.builder()
                        .model(electronicDto.getModel())
                        .brand(electronicDto.getBrand())
                        .name(electronicDto.getName())
                        .features(electronicDto.getFeatures())
                        .type(electronicDto.getType())
                        .availability(convertElectronic(electronicDto.getAvailability()))
                        .build())
                .build();
    }

    public List<ProductAvailabilityElectronic> convertElectronic(List<ElectronicDto.ElectronicAvailabilityDto> availability) {
        return availability.stream().map(item -> ProductAvailabilityElectronic.electronicAvailability()
                .color(item.getColor())
                .sku(item.getSku())
                .quantity(item.getQuantity())
                .protection(item.getProtection())
                .dimensions(
                        Dimensions.builder()
                                .length(item.getLength())
                                .width(item.getWidth())
                                .height(item.getHeight())
                                .build()
                )
                .build()).collect(Collectors.toList());
    }

    public ProductAvailability convertBook(AvailabilityDto availabilityDto) {
        return ProductAvailability.builder()
                .sku(availabilityDto.getSku())
                .quantity(availabilityDto.getQuantity())
                .protection(availabilityDto.getProtection())
                .dimensions(
                        Dimensions.builder()
                                .width(availabilityDto.getWidth())
                                .height(availabilityDto.getHeight())
                                .length(availabilityDto.getLength())
                                .build()
                )
                .build();
    }

}
