package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.frameworks.db.ProductRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductGateway {

    private final ProductRepository repository;

    public ProductGateway(ProductRepository repository) {
        this.repository = repository;
    }

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

    public void delete(Product product) {
        repository.delete(product);
    }

    public Product findBySku(ProductCategory category, String sku) {
        return findBySku(sku, category)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Product> findBySku(String sku, ProductCategory category) {
        if (sku == null || sku.isBlank() || category == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

       /* if (ProductCategory.CLOTHES.equals(category)) {
            return repository.findByClothesAvailabilitySku(sku);
        }

        if (ProductCategory.BOOKS.equals(category)) {
            return repository.findByBookAvailabilitySku(sku);
        }

        if (ProductCategory.ELECTRONICS.equals(category)) {
            return repository.findByElectronicAvailabilitySkuEquals(sku);
        }

        return repository.findByShoeAvailabilitySku(sku);*/
        return  Optional.empty();
    }
}
