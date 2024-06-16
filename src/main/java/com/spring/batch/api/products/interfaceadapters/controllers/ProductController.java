package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.products.BookPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.products.PresenterProduct;
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
import java.util.List;

@Component
public class ProductController {

    private PresenterProduct<? extends Product> presenter;

    private ProductGateway gateway;

    /*private ProductBusinessInterface business;*/

    @Autowired
    public ProductController(BookPresenter presenter, ProductGateway gateway) {
        this.presenter = presenter;
        this.gateway = gateway;
    }

    public ProductDto insert(ProductDto body) {
        Product product = presenter.convert(body);

        List<String> skus;


        return null;
    }

    public ProductDto findBySku(String sku, ProductCategory category) {
        /*Product product = gateway.findBySku(category, sku);

        return presenter.convert(product);*/
        return null;
    }

    public void updateStatus(String id, boolean status) {
        gateway.changeStatus(id, status);
    }

    public void changeQuantity(ProductCategory category, String sku, Integer quantity, LocalDateTime updatedAt, Integer protection) {
        /*Product product = gateway.findBySku(category, sku);

        business.updateQuantity(quantity, protection, updatedAt, product);

        gateway.update(product);*/
    }

    public ProductDto update(String id, ProductDto productDto) {
        Product product = gateway.findById(id);

        return null;
    }

    public void delete(String id) {
        Product product = gateway.findById(id);

        gateway.delete(product);
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
    /*


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
