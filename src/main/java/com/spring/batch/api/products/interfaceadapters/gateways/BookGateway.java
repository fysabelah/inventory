package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.frameworks.db.BookRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookGateway {

    private final BookRepository repository;

    public BookGateway(BookRepository repository) {
        this.repository = repository;
    }

    public Book findBySky(String sku) {
        return findBySku(sku)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Book> findBySku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findByAvailabilitySku(sku);
    }

    public Book insert(Book book) {
        return repository.insert(book);
    }
}
