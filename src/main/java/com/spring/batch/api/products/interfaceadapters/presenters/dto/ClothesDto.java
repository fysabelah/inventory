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

    @NotBlank
    @Schema(description = "Modelo da roupa", example = "Cintura baixa")
    @Size(max = 100)
    private String model;

    @NotBlank
    @Schema(description = "Marca da roupa", example = "Adidas")
    @Size(max = 100)
    private String brand;

    @NotNull
    private ProductSize size;

    @NotBlank
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50)
    private String color;

    @NotBlank
    @Schema(example = "Rasgada no joelho", description = "Nome da roupa")
    @Size(max = 100)
    private String name;
}
