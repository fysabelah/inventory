package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public interface PresenterProduct<T extends Product> {

    ProductDto convert(T entity);

    T convert(ProductDto dto);

    default List<ProductDto> convert(List<T> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }

        return documents.stream().map(this::convert).toList();
    }
}
