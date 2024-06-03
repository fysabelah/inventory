package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.frameworks.db.ProductRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductGateway {

    private final ProductRepository repository;

    public ProductGateway(ProductRepository repository) {
        this.repository = repository;
    }

    public Product findBySku(String sku) {
        return findBySkuOptional(sku)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Product> findBySkuOptional(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findBySku(sku);
    }

    public Product insert(Product product) {
        return repository.insert(product);
    }

    public Product update(Product product) {
        return repository.save(product);
    }
}
