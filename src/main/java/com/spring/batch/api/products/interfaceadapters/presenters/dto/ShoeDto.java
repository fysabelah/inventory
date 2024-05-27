package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoeDto extends ProductDto {

    @NotBlank
    @Schema(description = "Marca do sapato", example = "NIKE")
    @Size(max = 50)
    private String brand;

    @Pattern(regexp = "[0-9]{2}")
    @Schema(description = "Tamanho", example = "38")
    private String size;

    @NotBlank
    @Schema(example = "Sapato corredor", description = "Nome")
    @Size(max = 50)
    private String name;

    @NotBlank
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50)
    private String color;
}
