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
import java.util.Objects;
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

            products.forEach(product -> verifyCategoryAndCreateReservation(reservation, product));
        });

        Optional<Reservation> missingSku = reservations.stream()
                .filter(reservation -> reservation.getStatus() == null)
                .findFirst();

        if (missingSku.isPresent()) {
            throw new BusinessException("SKU_NOT_EXISTS", missingSku.get().getSku());
        }
    }

    private void verifyCategoryAndCreateReservation(Reservation reservation, Product product) {
        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            createBookReservation(product.getBook().getAvailability(), reservation);
        } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
            createElectronicReservation(product.getElectronic().getAvailability(), reservation);
        } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            createClothesReservation(product.getClothes().getAvailability(), reservation);
        } else {
            createShoesReservation(product.getShoes().getAvailability(), reservation);
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

    // As operações de stream utilizadas não são thread safe.
    public ProductAvailability updateReservationQuantity(Reservation reservation, Product product, Integer quantity) throws BusinessException {
        if (quantity == 0) {
            return cancelReservation(reservation, product);
        }

        return changeReservedQuantity(reservation, product, quantity);
    }

    private ProductAvailability changeReservedQuantity(Reservation reservation, Product product, Integer quantity) throws BusinessException {
        if (Objects.equals(reservation.getQuantity(), quantity)) {
            throw new BusinessException("SAME_QUANTITY_FOR_RESERVATION");
        }

        if (!ReservationStatus.READY.equals(reservation.getStatus())) {
            throw new BusinessException("RESERVATION_CANT_BE_UPDATED_ON_STATUS");
        }

        ProductAvailability availability = product.getAvailabilities()
                .stream()
                .filter(productAvailability -> productAvailability.getSku().equals(reservation.getSku()))
                .findFirst()
                .get();

        availability.setReservedQuantity(Math.abs(availability.getReservedQuantity() - reservation.getQuantity()));

        reservation.setQuantity(quantity);

        verifyIfCanChangeQuantity(reservation, availability);

        if (ReservationStatus.STOCKOUT.equals(reservation.getStatus())) {
            throw new BusinessException("RESERVATION_QUANTITY_UNAVAILABLE");
        }

        return availability;
    }

    private void verifyIfCanChangeQuantity(Reservation reservation, ProductAvailability availability) {
        int total = availability.getQuantity() - availability.getReservedQuantity() - availability.getProtection();

        if (total >= reservation.getQuantity()) {
            reservation.setStatus(ReservationStatus.READY);
            availability.setReservedQuantity(availability.getReservedQuantity() + reservation.getQuantity());
        } else {
            reservation.setStatus(ReservationStatus.STOCKOUT);
        }
    }

    private ProductAvailability cancelReservation(Reservation reservation, Product product) {
        reservation.setStatus(ReservationStatus.CANCELED);

        ProductAvailability productAvailability = product.getAvailabilities()
                .stream()
                .filter(availability -> availability.getSku().equals(reservation.getSku()))
                .findAny()
                .get();

        productAvailability.setReservedQuantity(Math.abs(productAvailability.getReservedQuantity() - reservation.getQuantity()));

        return productAvailability;
    }

    private Optional<ProductAvailability> findAvailabilityBySku(List<Product> products, String sku) {
        ProductCategory category = ProductCategory.valueOf(sku.split("-")[0]);

        return products.stream()
                .filter(product -> product.getCategory().equals(category))
                .flatMap(product -> product.getAvailabilities().stream())
                .filter(availability -> availability.getSku().equals(sku))
                .findFirst();
    }

    public void confirmReservation(List<Reservation> reservations, List<Product> products) {
        reservations.forEach(reservation -> {
            if (ReservationStatus.READY.equals(reservation.getStatus())) {
                ProductAvailability availability = findAvailabilityBySku(products, reservation.getSku()).get();

                if (availability.getQuantity() >= reservation.getQuantity()) {
                    availability.setQuantity(Math.max(availability.getQuantity() - reservation.getQuantity(), 0));

                    reservation.setStatus(ReservationStatus.FINISHED);
                } else {
                    reservation.setStatus(ReservationStatus.STOCKOUT);
                }

                availability.setReservedQuantity(Math.max(availability.getReservedQuantity() - reservation.getQuantity(), 0));
            }
        });
    }

    public void cancelReservation(List<Reservation> reservations, List<Product> products) {
        reservations.forEach(reservation -> {
            ProductAvailability availability = findAvailabilityBySku(products, reservation.getSku()).get();

            if (ReservationStatus.READY.equals(reservation.getStatus())) {
                availability.setReservedQuantity(Math.max(availability.getReservedQuantity() - reservation.getQuantity(), 0));
            }

            reservation.setStatus(ReservationStatus.CANCELED);
        });
    }
}
