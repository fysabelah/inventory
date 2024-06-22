package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.spring.batch.api.products.utils.enums.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClothesDto extends CategoryInformation {

    @NotBlank(message = "MODEL_CANT_BE_EMPTY")
    @Schema(description = "Modelo da roupa", example = "Cintura baixa")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String model;

    @NotBlank(message = "BRAND_CANT_BE_EMPTY")
    @Schema(description = "Marca da roupa", example = "Adidas")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String brand;

    @NotBlank(message = "COLOR_CANT_BE_EMPTY")
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String color;

    @NotBlank(message = "NAME_CANT_BE_EMPTY")
    @Schema(example = "Rasgada no joelho", description = "Nome da roupa")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String name;

    @NotNull
    @NotEmpty
    private List<ClothesAvailabilityDto> availability;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClothesAvailabilityDto extends AvailabilityDto {

        @NotNull
        private ProductSize size;

        @Builder(builderMethodName = "clothes")
        public ClothesAvailabilityDto(String sku, Integer quantity, Integer protection, BigDecimal width,
                                      BigDecimal length, BigDecimal height, ProductSize size, Integer reservedQuantity) {
            super(sku, quantity, protection, width, length, height, reservedQuantity);
            this.size = size;
        }
    }
}
