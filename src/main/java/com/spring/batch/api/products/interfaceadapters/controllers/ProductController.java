package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.ProductPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.enums.ProductSize;
import com.spring.batch.api.products.utils.pagination.PagedResponse;
import com.spring.batch.api.products.utils.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductController {

    private ProductPresenter presenter;

    private ProductGateway gateway;

    @Autowired
    public ProductController(ProductPresenter presenter, ProductGateway gateway) {
        this.presenter = presenter;
        this.gateway = gateway;
    }

    public ProductDto insert(ProductDto body) {
        return null;
    }

    public ProductDto findBySku(String sku, ProductCategory category) {
        return null;
    }

    public void updateStatus(String id, boolean status) {

    }

    public void changeQuantity(ProductCategory category, String sku, Integer quantity, LocalDateTime updatedAt, Integer protection) {

    }

    public ProductDto update(String id, ProductDto product) {
        return null;
    }

    public void delete(String id) {

    }

    public PagedResponse findAllBooks(String title, Genre genre, boolean status, Pagination page) {
        return null;
    }

    public PagedResponse findAllElectronics(String name, String brand, String model, ElectronicType electronicType, boolean status, Pagination page) {
        return null;
    }

    public PagedResponse findAllClothes(String name, String brand, String model, ProductSize size, boolean status, Pagination page) {
        return null;
    }

    public PagedResponse findAllShoes(String name, String brand, String size, boolean status, Pagination page) {
        return null;
    }
    /*protected BookPresenter presenter;

    protected GenericProductGateway gateway;

    protected ProductBusiness business;

    public ProductController(BookPresenter presenter, GenericProductGateway gateway, ProductBusiness business) {
        this.presenter = presenter;
        this.gateway = gateway;
        this.business = business;
    }

    public void updateStatus(String id, boolean status) {
        gateway.changeStatus(id, status);
    }

    public void changeQuantity(String sku, Integer quantity, LocalDateTime updatedAt, Integer protection) throws BusinessException {
        Product product = gateway.findBySku(sku);

        business.updateQuantity(quantity, protection, updatedAt, product);

        gateway.update(product);
    }

    public T update(String id, T productDto) throws BusinessException {
        Product product = gateway.findById(id);
        Product newInformation = presenter.convert(productDto);

        if (productDto.getClass().isInstance(ProductDto.class)) {
            business.updateProductInformation(product, productDto);
        } else {
            business.updateSpecificInformation(product, newInformation);
        }

        product = gateway.update(product);

        return presenter.convert(product);
    }

    public T findBySku(String sku) {
        return presenter.convert(gateway.findBySku(sku));
    }

    public void delete(String id) {
        Product product = gateway.findById(id);

        gateway.delete(product);
    }*/

    /* public T insert(T body) {
     *//* Product product = presenter.convert(body);*//*

        return null;
    }*/
}
