package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductController {

    @Autowired
    private ProductGateway gateway;

    public ProductDto insert(ProductDto productDto) {

        return null;
    }
}
