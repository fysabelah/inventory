package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ClothesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/product/clothes")
@Tag(name = "Roupas", description = "Métodos para operações com produtos do tipo roupas")
public class ClothesWeb {

    private final ProductController<ClothesDto> controller;

    public ClothesWeb(ProductController<ClothesDto> controller) {
        this.controller = controller;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ClothesDto> insert(@Valid @RequestBody ClothesDto clothes) {
        return ResponseEntity.ok(controller.insert(clothes));
    }
}
