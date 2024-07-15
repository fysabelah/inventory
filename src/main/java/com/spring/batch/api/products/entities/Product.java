package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Document(value = "products")
public class Product {

    @Id
    private String id;

    private String description;

    private BigDecimal value;

    private ProductCategory category;

    private boolean active;

    private Book book;

    private Electronic electronic;

    private Shoes shoes;

    private Clothes clothes;

    @Builder(builderMethodName = "product")
    public Product(String description, BigDecimal value, boolean active) {
        this.description = description;
        this.value = value;
        this.active = active;
    }

    public List<String> getSkus() {
        if (ProductCategory.SHOES.equals(this.category)) {
            return shoes.getAvailability().stream()
                    .map(ProductAvailability::getSku)
                    .toList();
        }

        if (ProductCategory.ELECTRONICS.equals(this.category)) {
            return electronic.getAvailability().stream()
                    .map(ProductAvailabilityElectronic::getSku)
                    .toList();
        }

        if (ProductCategory.CLOTHES.equals(this.category)) {
            return clothes.getAvailability().stream()
                    .map(ProductAvailabilityClothes::getSku)
                    .toList();
        }

        return book.getAvailability().getSku() != null ? List.of(book.getAvailability().getSku()) : Collections.emptyList();
    }

    public List<ProductAvailability> getAvailabilities() {
        if (ProductCategory.SHOES.equals(this.category)) {
            return new ArrayList<>(shoes.getAvailability());
        }

        if (ProductCategory.ELECTRONICS.equals(this.category)) {
            return new ArrayList<>(electronic.getAvailability());
        }

        if (ProductCategory.CLOTHES.equals(this.category)) {
            return new ArrayList<>(clothes.getAvailability());
        }

        return List.of(book.getAvailability());
    }
}
