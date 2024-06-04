package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(value = {"sku"}, allowGetters = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String sku;

    @Schema(description = "Quantidade de produtos", example = "15")
    @Min(value = 0, message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    @NotNull(message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    private Integer quantity;

    @Schema(example = "2", description = "Quantidade de produtos que não devem ser reservados na tentativa de diminuir quebra de estoque")
    @Min(value = 0, message = "PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE")
    @NotNull(message = "PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE")
    private Integer protection;

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

    @Builder
    public AvailabilityDto(ProductAvailability availability) {
        this.sku = availability.getSku();
        this.quantity = availability.getQuantity();
        this.protection = availability.getProtection();
        this.width = availability.getDimensions().getWidth();
        this.length = availability.getDimensions().getLength();
        this.height = availability.getDimensions().getHeight();
    }
}
