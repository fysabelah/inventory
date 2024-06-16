package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ProductAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.BookDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class BookPresenter implements PresenterProduct<Book> {

    private final ProductAvailabilityPresenter availabilityPresenter;

    public BookPresenter(ProductAvailabilityPresenter availabilityPresenter) {
        this.availabilityPresenter = availabilityPresenter;
    }

    @Override
    public ProductDto convert(Book entity) {
        ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setCategory(ProductCategory.BOOKS);
        productDto.setActive(entity.isActive());
        productDto.setDescription(entity.getDescription());
        productDto.setValue(productDto.getValue());

        BookDto bookDto = BookDto.builder()
                .isbn(entity.getIsbn())
                .title(entity.getTitle())
                .pages(entity.getPages())
                .genre(entity.getGenre())
                .publisher(entity.getPublisher())
                .availability(availabilityPresenter.convert(entity.getAvailability()))
                .build();

        productDto.setCategoryInformation(bookDto);

        return productDto;
    }

    @Override
    public Book convert(ProductDto dto) {
        BookDto bookDto = (BookDto) dto.getCategoryInformation();

        Book book = Book.builder()
                .isbn(bookDto.getIsbn())
                .title(bookDto.getTitle())
                .genre(bookDto.getGenre())
                .pages(bookDto.getPages())
                .publisher(bookDto.getPublisher())
                .availability(availabilityPresenter.convert(bookDto.getAvailability()))
                .build();

        book.setId(dto.getId());
        book.setDescription(dto.getDescription());
        book.setValue(dto.getValue());
        book.setActive(dto.isActive());

        return book;
    }

}
