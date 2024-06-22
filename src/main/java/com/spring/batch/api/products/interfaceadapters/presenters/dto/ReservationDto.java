package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.batch.api.products.utils.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"status"}, allowGetters = true)
public class ReservationDto {

    @Schema(description = "Identificação da reserva", example = "979re3er7787tre")
    private String id;

    @Schema(description = "Identificador do pedido a ser reservado", example = "BOOKS_785988-988")
    @NotBlank
    @NotEmpty
    private String sku;

    @Schema(description = "Quantidade de produtos", example = "15")
    @Min(value = 0, message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    @NotNull(message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE")
    private Integer quantity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private ReservationStatus status;
}
