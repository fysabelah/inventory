package com.spring.batch.api.products.entities;

import com.spring.batch.api.products.utils.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    private String title;

    private Integer pages;

    private Genre genre;

    private String publisher;
}
