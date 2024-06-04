package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.frameworks.db.ProductRepository;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.MessageUtil;
import jakarta.annotation.Resource;

import java.util.NoSuchElementException;

public abstract class GenericProductGateway {

    @Resource
    protected ProductRepository repository;

    public Product findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Product insert(Product product) {
        return repository.insert(product);
    }

    public Product update(Product product) {
        return repository.save(product);
    }

    public void changeStatus(String id, boolean status) {
        Product product = findById(id);
        product.setActive(status);

        repository.save(product);
    }

    public abstract Product findBySku(String sku);

    public void delete(Product product) {
        repository.delete(product);
    }
}
