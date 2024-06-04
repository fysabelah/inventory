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

    private String description;

    private ProductCategory category;

    private BigDecimal value;

    private boolean active;

    private Book book;

    private Clothes clothes;

    private Shoe shoe;

    private Electronic electronic;

    @Builder(builderMethodName = "book")
    public Product(String description, BigDecimal value, boolean active, Book book) {
        this.description = description;
        this.category = ProductCategory.BOOKS;
        this.value = value;
        this.active = active;
        this.book = book;
    }

    @Builder(builderMethodName = "shoe")
    public Product(String description, BigDecimal value, boolean active, Shoe shoe) {
        this.description = description;
        this.category = ProductCategory.SHOES;
        this.value = value;
        this.active = active;
        this.shoe = shoe;
    }

    @Builder(builderMethodName = "clothes")
    public Product(String description, BigDecimal value, boolean active, Clothes clothes) {
        this.description = description;
        this.category = ProductCategory.CLOTHES;
        this.value = value;
        this.active = active;
        this.clothes = clothes;
    }

    @Builder(builderMethodName = "electronic")
    public Product(String description, BigDecimal value, boolean active, Electronic electronic) {
        this.description = description;
        this.category = ProductCategory.ELECTRONICS;
        this.value = value;
        this.active = active;
        this.electronic = electronic;
    }
}
