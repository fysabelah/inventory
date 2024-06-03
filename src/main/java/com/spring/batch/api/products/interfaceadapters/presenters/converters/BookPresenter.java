package com.spring.batch.api.products.interfaceadapters.presenters.converters;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.entities.Dimensions;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.ProductAvailability;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookPresenter implements Presenter<Product, BookDto> {

    @Override
    public BookDto convert(Product entity) {
        return BookDto.builder()
                .pages(entity.getBook().getPages())
                .genre(entity.getBook().getGenre())
                .title(entity.getBook().getTitle())
                .isbn(entity.getBook().getIsbn())
                .publisher(entity.getBook().getPublisher())
                .product(entity)
                .build();
    }

    @Override
    public Product convert(BookDto dto) {
        return Product.book()
                .description(dto.getDescription())
                .availability(new ProductAvailability(dto.getQuantity(), dto.getProtection()))
                .value(dto.getValue())
                .dimensions(new Dimensions(dto.getWidth(), dto.getLength(), dto.getHeight()))
                .active(dto.isActive())
                .book(
                        Book.builder()
                                .isbn(dto.getIsbn())
                                .genre(dto.getGenre())
                                .pages(dto.getPages())
                                .publisher(dto.getPublisher())
                                .title(dto.getTitle())
                                .build()
                )
                .build();
    }
}
