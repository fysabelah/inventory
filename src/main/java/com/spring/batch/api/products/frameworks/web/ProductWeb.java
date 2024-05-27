package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductWeb {

    @Autowired
    private ProductController controller;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends ProductDto> insert(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(controller.insert(productDto));
    }
}
