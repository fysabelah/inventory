package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.spring.batch.api.products.utils.enums.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClothesDto extends ProductDto {

    @NotBlank(message = "MODEL_CANT_BE_EMPTY")
    @Schema(description = "Modelo da roupa", example = "Cintura baixa")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String model;

    @NotBlank(message = "BRAND_CANT_BE_EMPTY")
    @Schema(description = "Marca da roupa", example = "Adidas")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String brand;

    @NotNull
    private ProductSize size;

    @NotBlank(message = "COLOR_CANT_BE_EMPTY")
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String color;

    @NotBlank(message = "NAME_CANT_BE_EMPTY")
    @Schema(example = "Rasgada no joelho", description = "Nome da roupa")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String name;
}
