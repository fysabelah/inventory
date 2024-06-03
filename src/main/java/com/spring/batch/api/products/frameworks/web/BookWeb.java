package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.BookDto;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BookDto> insert(@RequestBody @Valid BookDto bookDto) throws BusinessException {
        return ResponseEntity.ok(controller.insert(bookDto));
    }

    @PutMapping(produces = "application/json", path = "/{sku}")
    public ResponseEntity<BookDto> update(@RequestParam String publisher,
                                          @RequestParam String title,
                                          @RequestParam Integer pages,
                                          @RequestParam Genre genre,
                                          @PathVariable String sku) throws BusinessException {
        controller.update(sku, publisher, title, pages, genre);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json", path = "/{sku}")
    public ResponseEntity<BookDto> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(controller.findBySku(sku));
    }

}
