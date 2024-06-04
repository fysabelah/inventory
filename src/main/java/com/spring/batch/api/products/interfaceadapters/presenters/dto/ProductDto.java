package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto extends Dto {

    @Schema(description = "Informações sobre o produto", example = "Calça jeans azul de cintura baixo com rasgo no joelho")
    @Size(max = 150, message = "MAXIMUM_SIZE_EXCEEDED")
    private String description;

    @Schema(description = "Define o tipo de payload a ser utilizado. Cada categoria possui seus campos a serem validados")
    private ProductCategory category;

    @Schema(example = "115.99", description = "Valor unitário do produto")
    @Min(value = 0)
    @NotNull
    private BigDecimal value;

    @Schema(example = "true", description = "Status do produto")
    private boolean active;

    @NotNull
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "category", include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = true)
    private CategoryInformation categoryInformation;

    @Builder
    public ProductDto(Product product) {
        this.setId(product.getId());
        this.description = product.getDescription();
        this.value = product.getValue();
        this.active = product.isActive();
        this.category = product.getCategory();

        if (ProductCategory.CLOTHES.equals(this.category)) {
            this.categoryInformation = ShoeDto.builder().shoe(product.getShoe()).build();
        } else if (ProductCategory.BOOKS.equals(this.category)) {
            this.categoryInformation = BookDto.builder().book(product.getBook()).build();
        } else if (ProductCategory.ELECTRONICS.equals(this.category)) {
            this.categoryInformation = ElectronicDto.builder().electronic(product.getElectronic()).build();
        } else {
            this.categoryInformation = ClothesDto.builder().clothes(product.getClothes()).build();
        }
    }
}
