package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String sku;

    private String description;

    private ProductCategory category;

    private ProductAvailability availability;

    private BigDecimal value;

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;

    private boolean active;

    private Book book;

    private Clothes clothes;

    private Shoe shoe;

    private Electronic electronic;
}
