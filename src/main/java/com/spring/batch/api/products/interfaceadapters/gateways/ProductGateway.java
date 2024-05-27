package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.frameworks.db.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductGateway {

    @Autowired
    private ProductRepository repository;


}
