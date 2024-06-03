package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.batch.api.products.utils.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto extends ProductDto {

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
}
