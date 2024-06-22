package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ReservationController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ReservationDto;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products/reservation")
@Tag(name = "Reserva", description = "Métodos para operações com reserva de produto")
public class ReservationWeb {

    private final ReservationController controller;

    public ReservationWeb(ReservationController controller) {
        this.controller = controller;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @Operation(summary = "Criar reserva")
    public ResponseEntity<List<ReservationDto>> insert(@RequestBody @Valid @NotEmpty List<ReservationDto> reservations) throws BusinessException {
        return ResponseEntity.ok(controller.insert(reservations));
    }

    @PutMapping(produces = "application/json", value = "/id/{id}/quantity/{quantity}")
    @Operation(summary = "Atualizar quantidade. Quando quantidade zero, a reserva será cancelada")
    public ResponseEntity<ReservationDto> update(@PathVariable String id, @PathVariable @PositiveOrZero Integer quantity) {
        controller.update(id, quantity);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/confirm")
    @Operation(summary = "Finalizar reserva. Confirma a retirada do produto. Dado concorrência, pode retornar STOCKOUT")
    public ResponseEntity<List<ReservationDto>> confirm(@Parameter(description = "Lista contendo os identificadores das reservas") @RequestBody @NotEmpty List<String> ids) {
        return ResponseEntity.ok(controller.confirm(ids));
    }

    @PutMapping(produces = "application/json", consumes = "application/json", value = "/cancel")
    @Operation(summary = "Cancelar a reserva. Cancela a retirada do produto. Reserva será cancelada")
    public ResponseEntity<List<ReservationDto>> cancel(@Parameter(description = "Lista contendo os identificadores das reservas") @RequestBody @NotEmpty List<String> ids) {
        return ResponseEntity.ok(controller.cancel(ids));
    }
}
