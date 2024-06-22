package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.*;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.pagination.PagedResponse;
import com.spring.batch.api.products.utils.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductPresenter {

    private final BookPresenter bookPresenter;

    private final ClothesPresenter clothesPresenter;

    private final ElectronicPresenter electronicPresenter;

    private final ShoePresenter shoePresenter;

    public ProductPresenter(BookPresenter bookPresenter, ClothesPresenter clothesPresenter, ElectronicPresenter electronicPresenter, ShoePresenter shoePresenter) {
        this.bookPresenter = bookPresenter;
        this.clothesPresenter = clothesPresenter;
        this.electronicPresenter = electronicPresenter;
        this.shoePresenter = shoePresenter;
    }

    public ProductDto convert(Product entity) {
        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setActive(entity.isActive());
        productDto.setDescription(entity.getDescription());
        productDto.setValue(entity.getValue());
        productDto.setCategory(entity.getCategory());

        if (ProductCategory.BOOKS.equals(entity.getCategory())) {
            productDto.setCategoryInformation(bookPresenter.convert(entity.getBook()));
        } else if (ProductCategory.CLOTHES.equals(entity.getCategory())) {
            productDto.setCategoryInformation(clothesPresenter.convert(entity.getClothes()));
        } else if (ProductCategory.ELECTRONICS.equals(entity.getCategory())) {
            productDto.setCategoryInformation(electronicPresenter.convert(entity.getElectronic()));
        } else if (ProductCategory.SHOES.equals(entity.getCategory())) {
            productDto.setCategoryInformation(shoePresenter.convert(entity.getShoes()));
        }

        return productDto;
    }

    public Product convert(ProductDto productDto) {
        Product entity = new Product();

        entity.setId(productDto.getId());
        entity.setActive(productDto.isActive());
        entity.setDescription(productDto.getDescription());
        entity.setValue(productDto.getValue());
        entity.setCategory(productDto.getCategory());

        if (ProductCategory.BOOKS.equals(productDto.getCategory())) {
            entity.setBook(bookPresenter.convert((BookDto) productDto.getCategoryInformation()));
        } else if (ProductCategory.CLOTHES.equals(productDto.getCategory())) {
            entity.setClothes(clothesPresenter.convert((ClothesDto) productDto.getCategoryInformation()));
        } else if (ProductCategory.ELECTRONICS.equals(productDto.getCategory())) {
            entity.setElectronic(electronicPresenter.convert((ElectronicDto) productDto.getCategoryInformation()));
        } else if (ProductCategory.SHOES.equals(productDto.getCategory())) {
            entity.setShoes(shoePresenter.convert((ShoeDto) productDto.getCategoryInformation()));
        }

        return entity;
    }

    public PagedResponse convert(Page<Product> page) {
        PagedResponse paged = new PagedResponse();

        paged.setPage(new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()));

        List<ProductDto> data = new ArrayList<>();

        List<Product> result = page.get().toList();

        if (!result.isEmpty()) {
            data = result.stream()
                    .map(this::convert)
                    .toList();
        }

        paged.setData(data);

        return paged;
    }
}
