package com.spring.batch.api.products.interfaceadapters.presenters.converters;

import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ReservationDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationPresenter {

    public Reservation convert(ReservationDto dto) {
        Reservation reservation = new Reservation();

        reservation.setId(dto.getId());
        reservation.setSku(dto.getSku());
        reservation.setStatus(dto.getStatus());
        reservation.setQuantity(dto.getQuantity());

        return reservation;
    }

    public ReservationDto convert(Reservation reservation) {
        ReservationDto dto = new ReservationDto();

        dto.setId(reservation.getId());
        dto.setSku(reservation.getSku());
        dto.setQuantity(reservation.getQuantity());
        dto.setStatus(reservation.getStatus());

        return dto;
    }

    public List<ReservationDto> convertToDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::convert)
                .toList();
    }

    public List<Reservation> convertToEntity(List<ReservationDto> dto) {
        return dto.stream()
                .map(this::convert)
                .toList();
    }
}
