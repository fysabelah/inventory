package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.utils.enums.Genre;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    private String isbn;

    private String title;

    private Integer pages;

    private Genre genre;

    private String publisher;

    private ProductAvailability availability;
}
