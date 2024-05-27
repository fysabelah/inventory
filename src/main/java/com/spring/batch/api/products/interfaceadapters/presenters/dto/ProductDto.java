package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
@JsonIgnoreProperties(value = {"sku"}, allowGetters = true)
public class ProductDto {

    private String sku;

    @Schema(description = "Informações sobre o produto", example = "Calça jeans azul de cintura baixo com rasgo no joelho")
    @Size(max = 150)
    private String description;

    @Schema(description = "Categoria do produto")
    @NotNull
    private ProductCategory category;

    @Schema(description = "Quantidade de produtos", example = "15")
    @Min(value = 0)
    private Integer quantity;

    @Schema(example = "2", description = "Quantidade de produtos que não devem ser reservados na tentativa de diminuir quebra de estoque")
    @Min(value = 0)
    private Integer protection;

    @Schema(example = "115.99", description = "Valor unitário do produto")
    @Min(value = 0)
    private BigDecimal value;

    @Schema(example = "2", description = "Largura do produto em cm")
    @PositiveOrZero
    private BigDecimal width;

    @Schema(example = "1.70", description = "Comprimento do produto em cm")
    @PositiveOrZero
    private BigDecimal length;

    @Schema(example = "30", description = "Altura do produto")
    @PositiveOrZero
    private BigDecimal height;

    @Schema(example = "true", description = "Status do produto")
    private boolean active;
}
