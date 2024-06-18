package com.spring.batch.api.products.interfaceadapters.controllers.implementation;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.interfaceadapters.controllers.interfaces.BookControllerInterface;
import com.spring.batch.api.products.interfaceadapters.gateways.BookGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.products.BookPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.usercase.BookBusiness;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookController implements BookControllerInterface {

    private final BookPresenter presenter;

    private final BookBusiness business;

    private final BookGateway gateway;

    public BookController(BookPresenter presenter, BookBusiness business, BookGateway gateway) {
        this.presenter = presenter;
        this.business = business;
        this.gateway = gateway;
    }

    @Override
    public ProductDto insertBook(ProductDto body) throws BusinessException {
        Book book = presenter.convert(body);

        business.updateToInsert(book);

        String sku = book.getAvailability().getSku();

        Optional<Book> optional = gateway.findBySku(sku);

        if (optional.isPresent()) {
            throw new BusinessException("PRODUCT_ALREADY_REGISTERED");
        }

        book = gateway.insert(book);

        return presenter.convert(book);
    }
}
