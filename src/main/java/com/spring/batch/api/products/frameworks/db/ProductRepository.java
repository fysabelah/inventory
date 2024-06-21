package com.spring.batch.api.products.frameworks.db;

import com.spring.batch.api.products.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

    @Query("{ $or: [ { 'book.availability.sku': ?0 }, { 'electronic.availability.sku': ?0 }, { 'shoes.availability.sku': ?0 }, { 'clothes.availability.sku': ?0 } ] }")
    Optional<Product> findBySku(String sku);
}
