package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.BookDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/product/books")
@Tag(name = "Livros", description = "Métodos para operações com produtos do tipo livro")
public class BookWeb {

    private final ProductController<BookDto> controller;

    @Autowired
    public BookWeb(ProductController<BookDto> controller) {
        this.controller = controller;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookDto> insert(@RequestBody @Valid BookDto bookDto) {
        return ResponseEntity.ok(controller.insert(bookDto));
    }
}
