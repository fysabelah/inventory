package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.enums.ReservationStatus;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ReservationBusiness {

    private final Clock clock;

    public ReservationBusiness(Clock clock) {
        this.clock = clock;
    }

    public void create(List<Reservation> reservations, List<Product> products) throws BusinessException {
        reservations.forEach(reservation -> {
            reservation.setDate(LocalDateTime.now(clock));
            reservation.setId(null);

            products.forEach(product -> {
                if (ProductCategory.BOOKS.equals(product.getCategory())) {
                    createBookReservation(product.getBook().getAvailability(), reservation);
                } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
                    createElectronicReservation(product.getElectronic().getAvailability(), reservation);
                } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
                    createClothesReservation(product.getClothes().getAvailability(), reservation);
                } else {
                    createShoesReservation(product.getShoes().getAvailability(), reservation);
                }
            });
        });

        Optional<Reservation> missingSku = reservations.stream()
                .filter(reservation -> reservation.getStatus() == null)
                .findFirst();

        if (missingSku.isPresent()) {
            throw new BusinessException("SKU_NOT_EXISTS", missingSku.get().getSku());
        }
    }

    private void createShoesReservation(Set<ProductAvailabilityShoe> availabilities, Reservation reservation) {
        for (ProductAvailabilityShoe availability : availabilities) {
            if (availability.getSku().equals(reservation.getSku())) {
                checkQuantityToReservation(availability, reservation);
                break;
            }
        }
    }

    private void createClothesReservation(Set<ProductAvailabilityClothes> availabilities, Reservation reservation) {
        for (ProductAvailabilityClothes availability : availabilities) {
            if (availability.getSku().equals(reservation.getSku())) {
                checkQuantityToReservation(availability, reservation);
                break;
            }
        }
    }

    private void createElectronicReservation(Set<ProductAvailabilityElectronic> availabilities, Reservation reservation) {
        for (ProductAvailabilityElectronic availability : availabilities) {
            if (availability.getSku().equals(reservation.getSku())) {
                checkQuantityToReservation(availability, reservation);
                break;
            }
        }
    }

    private void checkQuantityToReservation(ProductAvailability availability, Reservation reservation) {
        int total = availability.getQuantity() - availability.getReservedQuantity() - availability.getProtection();

        if (total >= reservation.getQuantity()) {
            reservation.setStatus(ReservationStatus.READY);
            availability.setReservedQuantity(availability.getReservedQuantity() + reservation.getQuantity());
        } else {
            reservation.setStatus(ReservationStatus.STOCKOUT);
        }
    }

    private void createBookReservation(ProductAvailability availability, Reservation reservation) {
        if (availability.getSku().equals(reservation.getSku())) {
            checkQuantityToReservation(availability, reservation);
        }
    }


    public void updateReservationQuantity(Reservation reservation, Product product, Integer quantity) {

    }
}
