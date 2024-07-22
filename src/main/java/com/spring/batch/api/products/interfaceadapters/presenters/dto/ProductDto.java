package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class ProductDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Informações sobre o produto", example = "Um livro muito interessante")
    @Size(max = 150, message = "MAXIMUM_SIZE_EXCEEDED")
    private String description;

    @Schema(description = "Define o tipo de payload a ser utilizado. Cada categoria possui seus campos a serem validados", example = "BOOKS")
    private ProductCategory category;

    @Schema(example = "115.99", description = "Valor unitário do produto")
    @NotNull
    @PositiveOrZero
    private BigDecimal value;

    @Schema(example = "true", description = "Status do produto")
    private boolean active;

    @NotNull
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = true)
    private CategoryInformation categoryInformation;

}
