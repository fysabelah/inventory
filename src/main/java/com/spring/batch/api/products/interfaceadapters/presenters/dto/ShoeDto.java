package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.spring.batch.api.products.entities.Shoe;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    public static class ShoeAvailabilityDto extends AvailabilityDto {

        @Pattern(regexp = "\\d{2}")
        @Schema(description = "Tamanho", example = "38")
        private String size;

        public ShoeAvailabilityDto(ProductAvailabilityShoe availability) {
            super(availability);
            this.size = availability.getSize();
        }
    }

    @Builder
    public ShoeDto(Shoe shoe) {
        this.brand = shoe.getBrand();
        this.name = shoe.getName();
        this.color = shoe.getColor();
        this.availability = shoe.getAvailability().stream()
                .map(ShoeAvailabilityDto::new)
                .collect(Collectors.toList());
    }
}
