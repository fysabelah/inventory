package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.Presenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.usercase.ProductBusiness;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductController<T extends ProductDto> {

    private final ProductGateway gateway;

    private final Presenter<Product, T> presenter;

    private final ProductBusiness business;

    public ProductController(ProductGateway gateway, Presenter<Product, T> presenter, ProductBusiness business) {
        this.gateway = gateway;
        this.presenter = presenter;
        this.business = business;
    }

    public T insert(T productDto) throws BusinessException {
        Product product = presenter.convert(productDto);

        String sku = business.createSku(product);

        Optional<Product> optionalProduct = gateway.findBySkuOptional(sku);

        if (optionalProduct.isPresent()) {
            throw new BusinessException("PRODUCT_ALREADY_REGISTERED");
        }

        business.updateToInsert(sku, product);

        product = gateway.insert(product);

        return presenter.convert(product);
    }

    public void update(String sku, String publisher, String title, Integer pages, Genre genre) throws BusinessException {
        Product product = gateway.findBySku(sku);

        business.verifyAndUpdateBook(title, pages, genre, product, publisher);

        gateway.update(product);
    }

    public T findBySku(String sku) {
        return presenter.convert(gateway.findBySku(sku));
    }
}
