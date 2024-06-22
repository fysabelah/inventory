package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShoeDto extends CategoryInformation {

    @NotBlank(message = "BRAND_CANT_BE_EMPTY")
    @Schema(description = "Marca do sapato", example = "NIKE")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String brand;

    @NotBlank(message = "NAME_CANT_BE_EMPTY")
    @Schema(example = "Sapato corredor", description = "Nome")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String name;

    @NotBlank(message = "COLOR_CANT_BE_EMPTY")
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String color;

    @NotNull
    @NotEmpty
    private List<ShoeAvailabilityDto> availability;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShoeAvailabilityDto extends AvailabilityDto {

        @Pattern(regexp = "\\d{2}")
        @Schema(description = "Tamanho", example = "38")
        private String size;

        @Builder(builderMethodName = "shoes")
        public ShoeAvailabilityDto(String sku, Integer quantity, Integer protection, BigDecimal width, BigDecimal length,
                                   BigDecimal height, String size, Integer reservedQuantity) {
            super(sku, quantity, protection, width, length, height, reservedQuantity);
            this.size = size;
        }
    }
}
