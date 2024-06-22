package com.spring.batch.api.products.frameworks.db;


import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface ProductRepositoryCustom {

    List<Product> findBySkus(List<String> skus);

    Page<Product> findAllElectronics(String name, String brand, String model, ElectronicType electronicType, boolean status, Pageable page);

    Page<Product> findAllClothes(String name, String brand, String model, ProductSize size, boolean status, Pageable page);

    Page<Product> findAllShoes(String name, String brand, String size, boolean status, Pageable page);

    Page<Product> findAllBooks(String title, Genre genre, boolean status, Pageable page);
}
