package com.spring.batch.api.products.interfaceadapters.controllers;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.interfaceadapters.gateways.ProductGateway;
import com.spring.batch.api.products.interfaceadapters.gateways.ReservationGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.ReservationPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ReservationDto;
import com.spring.batch.api.products.usercase.ReservationBusiness;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationController {

    private final ReservationGateway gateway;

    private final ReservationBusiness business;

    private final ReservationPresenter presenter;

    private final ProductGateway productGateway;

    public ReservationController(ReservationGateway gateway, ReservationBusiness business, ReservationPresenter presenter, ProductGateway productGateway) {
        this.gateway = gateway;
        this.business = business;
        this.presenter = presenter;
        this.productGateway = productGateway;
    }

    public List<ReservationDto> insert(List<ReservationDto> dtos) throws BusinessException {
        List<Reservation> reservations = presenter.convertToEntity(dtos);

        List<String> skus = reservations.stream()
                .map(Reservation::getSku)
                .toList();

        List<Product> products = productGateway.findBySkus(skus);

        if (products.isEmpty()) {
            throw new BusinessException("RESERVATION_WITH_SKU_NOT_SAVED");
        }

        business.create(reservations, products);

        reservations = gateway.insert(reservations);

        productGateway.update(products);

        return presenter.convertToDto(reservations);
    }

    public List<ReservationDto> confirm(List<String> ids) {
        return null;
    }

    public List<ReservationDto> cancel(List<String> ids) {
        return null;
    }

    public ReservationDto update(String id, Integer quantity) {
        Reservation reservation = gateway.findById(id);

        Product product = productGateway.findBySku(reservation.getSku());

        business.updateReservationQuantity(reservation, product, quantity);

        reservation = gateway.update(reservation);

        productGateway.update(product);

        return presenter.convert(reservation);
    }
}
