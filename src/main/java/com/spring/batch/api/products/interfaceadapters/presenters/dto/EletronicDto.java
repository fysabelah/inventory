package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.spring.batch.api.products.utils.enums.EletronicType;
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
public class EletronicDto extends ProductDto {

    @NotBlank(message = "MODEL_CANT_BE_EMPTY")
    @Schema(example = "Modelo", description = "3310")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String model;

    @NotBlank(message = "BRAND_CANT_BE_EMPTY")
    @Schema(description = "Marca", example = "Nokia")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String brand;

    @NotBlank(message = "COLOR_CANT_BE_EMPTY")
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String color;

    @NotBlank(message = "NAME_CANT_BE_EMPTY")
    @Schema(example = "Nokia quadrado", description = "Nome")
    @Size(max = 100, message = "MAXIMUM_SIZE_EXCEEDED")
    private String name;

    @Schema(example = "Excelente jogo da cobrinha", description = "Características")
    @Size(max = 250, message = "MAXIMUM_SIZE_EXCEEDED")
    private String features;

    @NotNull
    @Schema(example = "CELL_PHONE", description = "Tipo")
    private EletronicType type;
}
