package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ProductAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
class BookPresenter {

    private final ProductAvailabilityPresenter availabilityPresenter;

    public BookPresenter(ProductAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    public BookDto convert(Book entity) {
        return BookDto.builder()
                .isbn(entity.getIsbn())
                .title(entity.getTitle())
                .pages(entity.getPages())
                .genre(entity.getGenre())
                .publisher(entity.getPublisher())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();
    }

    public Book convert(BookDto bookDto) {
        return Book.builder()
                .isbn(bookDto.getIsbn())
                .title(bookDto.getTitle())
                .genre(bookDto.getGenre())
                .pages(bookDto.getPages())
                .publisher(bookDto.getPublisher())
                .availability(availabilityPresenter.convert(bookDto.getAvailability()))
                .build();
    }

}
