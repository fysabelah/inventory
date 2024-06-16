package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Product;

import java.time.LocalDateTime;

public interface ProductBusinessInterface extends BookBusinessInterface, ElectronicBusinessInterface {

    void updateQuantity(Integer quantity, Integer protection, LocalDateTime updatedAt, Product product);
}
