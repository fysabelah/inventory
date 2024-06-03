package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto extends ProductDto {

    @Schema(description = "Número de livro internacional padronizado", example = "978-0-596-52068-7")
    @NotBlank
    @NotNull
    private String isbn;

    @Schema(description = "Título do livro", example = "Era uma vez")
    @NotBlank(message = "BOOK_WITHOUT_TITLE")
    @Size(max = 200, message = "MAXIMUM_SIZE_EXCEEDED")
    private String title;

    @Positive(message = "BOOK_WITHOUT_PAGE")
    @Schema(description = "Quantidade de páginas", example = "500")
    private Integer pages;

    @NotNull
    @Schema(description = "Gênero do livro")
    private Genre genre;

    @Schema(description = "Editora do livro", example = "Editora")
    @NotBlank(message = "BOOK_WITHOUT_PUBLISHER")
    private String publisher;

    @Builder
    public BookDto(String title, Integer pages, Genre genre, String publisher, String isbn, Product product) {
        super(product);
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.genre = genre;
        this.publisher = publisher;
    }
}
