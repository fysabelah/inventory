package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductController<T extends ProductDto> {

    @Autowired
    private ProductGateway gateway;

    public T insert(T productDto) {
        return null;
    }
}
