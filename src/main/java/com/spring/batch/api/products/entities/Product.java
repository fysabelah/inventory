package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.ProductCategory;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(value = "products")
public class Product {

    @Id
    private String id;

    private String sku;

    private String description;

    private ProductCategory category;

    private ProductAvailability availability;

    private BigDecimal value;

    private Dimensions dimensions;

    private boolean active;

    private Book book;

    private Clothes clothes;

    private Shoe shoe;

    private Electronic electronic;

    @Builder(builderMethodName = "book")
    public Product(String sku, String description, ProductAvailability availability, BigDecimal value, Dimensions dimensions, boolean active, Book book) {
        this.sku = sku;
        this.description = description;
        this.category = ProductCategory.BOOKS;
        this.availability = availability;
        this.value = value;
        this.dimensions = dimensions;
        this.active = active;
        this.book = book;
    }
}
