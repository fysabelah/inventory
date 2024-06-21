package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.frameworks.db.ProductRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void delete(Product product) {
        repository.delete(product);
    }

    public List<Product> findBySkus(List<String> skus) {
        if (skus == null || skus.isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findBySkus(skus);
    }

    public Product findBySkus(String sku) {
        return findBySkuOptional(sku)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Product> findBySkuOptional(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findBySku(sku);
    }

    public Page<Product> findAll(String title, Genre genre, boolean status, Pageable page) {
        return repository.findAllBooks(title, genre, status, page);
    }

    public Page<Product> findAll(String name, String brand, String model, ElectronicType electronicType, boolean status, Pageable page) {
        return repository.findAllElectronics(name, brand, model, electronicType, status, page);
    }

    public Page<Product> findAll(String name, String brand, String model, ProductSize size, boolean status, Pageable page) {
        return repository.findAllClothes(name, brand, model, size, status, page);
    }

    public Page<Product> findAll(String name, String brand, String size, boolean status, Pageable page) {
        return repository.findAllShoes(name, brand, size, status, page);
    }
}
