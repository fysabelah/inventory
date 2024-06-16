package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /*Optional<Product> findByBookAvailabilitySku(String sku);

    Optional<Product> findByClothesAvailabilitySku(String sku);

    Optional<Product> findByShoeAvailabilitySku(String sku);

    Product findByElectronicAvailabilitySkuIn(List<String> skus);

    Optional<Product> findByElectronicAvailabilitySkuEquals(String sku);*/
}
