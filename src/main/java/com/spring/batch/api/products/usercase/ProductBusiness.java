package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.*;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class ProductBusiness {

    protected final Clock clock;

    protected ProductBusiness(Clock clock) {
        this.clock = clock;
    }

    public String getSku(List<String> fields, ProductCategory category) {
        List<String> normalized = new ArrayList<>();

        for (String field : fields) {
            normalized.add(normalize(field));
        }

        return String.join("-", normalized);
    }

    private String normalize(String value) {
        return value.replaceAll("\\s", "-");
    }

    protected String createSku(List<String> fields, ProductCategory category) {
        List<String> normalized = new ArrayList<>();

        for (String field : fields) {
            normalized.add(normalize(field));
        }

        return category.name().concat("-").concat(String.join("-", normalized));
    }

    protected String createSku(String field, ProductCategory category) {
        return category.name().concat("-").concat(normalize(field));
    }

    protected void checkIfShouldUpdateQuantity(LocalDateTime lastUpdated, LocalDateTime updatedAt, Integer newQuantity, Integer protection) throws BusinessException {
        if (lastUpdated.isAfter(updatedAt)) {
            throw new BusinessException("PRODUCT_HAS_LATEST_UPDATE");
        }

        if (newQuantity < 0) {
            throw new BusinessException("AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE");
        }

        if (protection < 0) {
            throw new BusinessException("PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE");
        }
    }

    public void updateProductInformation(Product product, ProductDto productDto) {
        product.setActive(productDto.isActive());
        product.setDescription(productDto.getDescription());
        product.setValue(productDto.getValue());
    }

    public void updateQuantity(Integer quantity, Integer protection, LocalDateTime updatedAt, Product product) throws BusinessException {


    }

    public void updateSpecificInformation(Product toUpdate, Product newInformation) throws BusinessException {

    }

    public void updateToInsert(Product product) {
        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            updateToInsert(product.getBook());
        } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
            updateToInsert(product.getElectronic());
        } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            insertToUpdate(product.getClothes());
        } else {
            insertToUpdate(product.getShoes());
        }
    }

    private void insertToUpdate(Shoes shoes) {
        String name = shoes.getName();
        String brand = shoes.getBrand();
        String color = shoes.getColor();

        shoes.getAvailability().forEach(productAvailabilityShoe -> {
            productAvailabilityShoe.setUpdatedAt(LocalDateTime.now(clock));
            productAvailabilityShoe.setSku(
                    createSku(
                            List.of(name, brand, color, productAvailabilityShoe.getSize()),
                            ProductCategory.SHOES
                    )
            );
        });
    }

    private void updateToInsert(Book book) {
        book.getAvailability().setUpdatedAt(LocalDateTime.now(clock));
        book.getAvailability().setSku(createSku(book.getIsbn(), ProductCategory.BOOKS));
    }

    private void updateToInsert(Electronic electronic) {
        String model = electronic.getModel();
        String brand = electronic.getBrand();
        ElectronicType type = electronic.getType();

        electronic.getAvailability().forEach(availability -> {
            availability.setSku(
                    createSku(List.of(model, brand, type.name(), availability.getColor()),
                            ProductCategory.ELECTRONICS)
            );

            availability.setUpdatedAt(LocalDateTime.now(clock));
        });
    }

    private void insertToUpdate(Clothes clothes) {
        String model = clothes.getModel();
        String brand = clothes.getBrand();
        String color = clothes.getColor();

        clothes.getAvailability().forEach(availability -> {
            availability.setUpdatedAt(LocalDateTime.now(clock));
            availability.setSku(createSku(List.of(model, brand, color, availability.getSize().name()), ProductCategory.CLOTHES));
        });
    }

    public void updateQuantity(Integer quantity, LocalDateTime updatedAt, Integer protection, String sku, Product product) {

    }

    public void update(Product saved, Product product) {

    }
}
