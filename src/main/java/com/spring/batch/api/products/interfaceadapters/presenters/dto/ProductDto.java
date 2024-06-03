package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.batch.api.products.entities.Product;
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
@JsonIgnoreProperties(value = {"sku", "category", "id"}, allowGetters = true)
public class ProductDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String sku;

    @Schema(description = "Informações sobre o produto", example = "Calça jeans azul de cintura baixo com rasgo no joelho")
    @Size(max = 150, message = "MAXIMUM_SIZE_EXCEEDED")
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private ProductCategory category;

    @Schema(description = "Quantidade de produtos", example = "15")
    @Min(value = 0, message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    @NotNull(message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    private Integer quantity;

    @Schema(example = "2", description = "Quantidade de produtos que não devem ser reservados na tentativa de diminuir quebra de estoque")
    @Min(value = 0, message = "PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE")
    @NotNull(message = "PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE")
    private Integer protection;

    @Schema(example = "115.99", description = "Valor unitário do produto")
    @Min(value = 0)
    @NotNull
    private BigDecimal value;

    @Schema(example = "2", description = "Largura do produto em cm")
    @PositiveOrZero
    @NotNull
    private BigDecimal width;

    @Schema(example = "1.70", description = "Comprimento do produto em cm")
    @PositiveOrZero
    @NotNull
    private BigDecimal length;

    @Schema(example = "30", description = "Altura do produto")
    @PositiveOrZero
    @NotNull
    private BigDecimal height;

    @Schema(example = "true", description = "Status do produto")
    private boolean active;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.quantity = product.getAvailability().getQuantity();
        this.protection = product.getAvailability().getProtection();
        this.value = product.getValue();
        this.width = product.getDimensions().getWidth();
        this.length = product.getDimensions().getLength();
        this.height = product.getDimensions().getHeight();
        this.active = product.isActive();
    }
}
