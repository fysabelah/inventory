package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.products.ProductPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.usercase.ProductBusiness;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductSize;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import com.spring.batch.api.products.utils.pagination.PagedResponse;
import com.spring.batch.api.products.utils.pagination.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductController {

    private final ProductPresenter presenter;

    private final ProductBusiness business;

    private final ProductGateway gateway;

    public ProductController(ProductPresenter presenter, ProductBusiness business, ProductGateway gateway) {
        this.presenter = presenter;
        this.business = business;
        this.gateway = gateway;
    }

    public ProductDto insert(ProductDto body) throws BusinessException {
        Product product = presenter.convert(body);

        business.updateToInsert(product);

        List<String> skus = product.getSkus();

        List<Product> products = gateway.findBySkus(skus);

        if (!products.isEmpty()) {
            throw new BusinessException("PRODUCT_ALREADY_REGISTERED");
        }

        product = gateway.insert(product);

        return presenter.convert(product);
    }

    public ProductDto findBySku(String sku) {
        Product product = gateway.findBySkus(sku);

        return presenter.convert(product);
    }

    public void updateStatus(String id, boolean status) {
        Product product = gateway.findById(id);

        product.setActive(status);

        gateway.update(product);
    }

    public void changeQuantity(String sku, Integer quantity, LocalDateTime updatedAt, Integer protection) throws BusinessException {
        Product product = gateway.findBySkus(sku);

        business.updateQuantity(quantity, updatedAt, protection, sku, product);

        gateway.update(product);
    }

    public void delete(String id) {
        Product product = gateway.findById(id);

        gateway.delete(product);
    }

    public PagedResponse findAllBooks(String title, Genre genre, boolean status, Pagination pagination) {
        Pageable page = PageRequest.of(pagination.getPage(),
                pagination.getPageSize(),
                Sort.by(Sort.Direction.ASC, "book.title"));

        Page<Product> products = gateway.findAll(title,
                genre, status, page);

        return presenter.convert(products);
    }

    public PagedResponse findAllElectronics(String name, String brand, String model, ElectronicType electronicType, boolean status, Pagination pagination) {
        Pageable page = PageRequest.of(pagination.getPage(),
                pagination.getPageSize(),
                Sort.by(Sort.Direction.ASC, "electronic.name"));

        Page<Product> products = gateway.findAll(name,
                brand, model, electronicType, status, page);

        return presenter.convert(products);
    }

    public PagedResponse findAllClothes(String name, String brand, String model, ProductSize size, boolean status, Pagination pagination) {
        Pageable page = PageRequest.of(pagination.getPage(),
                pagination.getPageSize(),
                Sort.by(Sort.Direction.ASC, "clothes.name"));

        Page<Product> products = gateway.findAll(name,
                brand, model, size, status, page);

        return presenter.convert(products);
    }

    public PagedResponse findAllShoes(String name, String brand, String size, boolean status, Pagination pagination) {
        Pageable page = PageRequest.of(pagination.getPage(),
                pagination.getPageSize(),
                Sort.by(Sort.Direction.ASC, "shoes.name"));

        Page<Product> products = gateway.findAll(name,
                brand, size, status, page);

        return presenter.convert(products);
    }

    public ProductDto updateValue(String id, BigDecimal value) throws BusinessException {
        Product product = gateway.findById(id);

        business.checkIfUpdateValue(product, value);

        product = gateway.update(product);

        return presenter.convert(product);
    }
}
