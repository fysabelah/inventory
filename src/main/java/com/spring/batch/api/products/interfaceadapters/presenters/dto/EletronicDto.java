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

    @NotBlank
    @Schema(example = "Modelo", description = "3310")
    @Size(max = 50)
    private String model;

    @NotBlank
    @Schema(description = "Marca", example = "Nokia")
    @Size(max = 50)
    private String brand;

    @NotBlank
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50)
    private String color;

    @NotBlank
    @Schema(example = "Nokia quadrado", description = "Nome")
    @Size(max = 100)
    private String name;

    @Schema(example = "Excelente jogo da cobrinha", description = "Características")
    private String features;

    @NotNull
    @Schema(example = "CELL_PHONE", description = "Tipo")
    private EletronicType type;
}
