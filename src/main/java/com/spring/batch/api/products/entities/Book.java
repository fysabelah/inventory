package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.utils.enums.Genre;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "books")
public class Book extends Product {

    private String isbn;

    private String title;

    private Integer pages;

    private Genre genre;

    private String publisher;

    private ProductAvailability availability;
}
