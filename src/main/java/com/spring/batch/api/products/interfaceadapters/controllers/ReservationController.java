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

        List<Product> products = getProducts(reservations);

        if (products.isEmpty()) {
            throw new BusinessException("RESERVATION_WITH_SKU_NOT_SAVED");
        }

        business.create(reservations, products);

        reservations = gateway.insert(reservations);

        productGateway.update(products);

        return presenter.convertToDto(reservations);
    }

    private List<Product> getProducts(List<Reservation> reservations) {
        List<String> skus = reservations.stream()
                .map(Reservation::getSku)
                .toList();

        return productGateway.findBySkus(skus);
    }

    public List<ReservationDto> confirm(List<String> ids) throws BusinessException {
        List<Reservation> reservations = gateway.findById(ids);

        checkIfMissingReservation(ids, reservations);

        List<Product> products = getProducts(reservations);

        business.confirmReservation(reservations, products);

        reservations = gateway.update(reservations);

        productGateway.update(products);

        return presenter.convertToDto(reservations);
    }

    private static void checkIfMissingReservation(List<String> ids, List<Reservation> reservations) throws BusinessException {
        if (reservations.size() != ids.size()) {
            List<String> found = new java.util.ArrayList<>(reservations.stream()
                    .map(Reservation::getId)
                    .toList());

            ids.removeAll(found);

            if (!ids.isEmpty()) {
                throw new BusinessException("RESERVATIONS_NOT_FOUND", ids.toString());
            }
        }
    }

    public List<ReservationDto> cancel(List<String> ids) throws BusinessException {
        List<Reservation> reservations = gateway.findById(ids);

        checkIfMissingReservation(ids, reservations);

        List<Product> products = getProducts(reservations);

        business.cancelReservation(reservations, products);

        reservations = gateway.update(reservations);

        productGateway.update(products);

        return presenter.convertToDto(reservations);
    }

    public ReservationDto update(String id, Integer quantity) throws BusinessException {
        Reservation reservation = gateway.findById(id);

        Product product = productGateway.findBySku(reservation.getSku());

        business.updateReservationQuantity(reservation, product, quantity);

        reservation = gateway.update(reservation);

        productGateway.update(product);

        return presenter.convert(reservation);
    }
}
