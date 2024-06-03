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

    @NotBlank(message = "BRAND_CANT_BE_EMPTY")
    @Schema(description = "Marca do sapato", example = "NIKE")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String brand;

    @Pattern(regexp = "[0-9]{2}")
    @Schema(description = "Tamanho", example = "38")
    private String size;

    @NotBlank(message = "NAME_CANT_BE_EMPTY")
    @Schema(example = "Sapato corredor", description = "Nome")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String name;

    @NotBlank(message = "COLOR_CANT_BE_EMPTY")
    @Schema(description = "Cor", example = "Amarelo")
    @Size(max = 50, message = "MAXIMUM_SIZE_EXCEEDED")
    private String color;
}
