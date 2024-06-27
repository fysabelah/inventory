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
import java.util.*;

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

    public void updateReservationQuantity(Reservation reservation, Product product, Integer quantity) throws BusinessException {
        if (quantity == 0) {
            cancelReservation(reservation, product);
        } else {
            changeReservedQuantity(reservation, product, quantity);
        }
    }

    private void changeReservedQuantity(Reservation reservation, Product product, Integer quantity) throws BusinessException {
        if (Objects.equals(reservation.getQuantity(), quantity)) {
            throw new BusinessException("SAME_QUANTITY_FOR_RESERVATION");
        }

        if (!ReservationStatus.READY.equals(reservation.getStatus())) {
            throw new BusinessException("RESERVATION_CANT_BE_UPDATED_ON_STATUS");
        }

        Reservation toCheck = new Reservation();
        toCheck.setSku(reservation.getSku());
        toCheck.setQuantity(Math.abs(reservation.getQuantity() - quantity));

        verifyCategoryAndCreateReservation(reservation, product);

        if (ReservationStatus.STOCKOUT.equals(reservation.getStatus())) {
            throw new BusinessException("RESERVATION_QUANTITY_UNAVAILABLE");
        }

        reservation.setQuantity(quantity);
    }

    private void cancelReservation(Reservation reservation, Product product) {
        reservation.setStatus(ReservationStatus.CANCELED);

        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            product.getBook().getAvailability().setReservedQuantity(
                    product.getBook().getAvailability().getReservedQuantity() - reservation.getQuantity()
            );
        } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
            cancelElectronicReservation(product.getElectronic().getAvailability(), reservation.getQuantity(), reservation.getSku());
        } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            cancelClothesReservation(product.getClothes().getAvailability(), reservation.getQuantity(), reservation.getSku());
        } else {
            cancelShoesReservation(product.getShoes().getAvailability(), reservation.getQuantity(), reservation.getSku());
        }
    }

    private void cancelShoesReservation(Set<ProductAvailabilityShoe> availabilities, Integer quantity, String sku) {
        for (ProductAvailabilityShoe availability : availabilities) {
            if (availability.getSku().equals(sku)) {
                availability.setReservedQuantity(availability.getReservedQuantity() - quantity);
                break;
            }
        }
    }

    private void cancelClothesReservation(Set<ProductAvailabilityClothes> availabilities, Integer quantity, String sku) {
        for (ProductAvailabilityClothes availability : availabilities) {
            if (availability.getSku().equals(sku)) {
                availability.setReservedQuantity(availability.getReservedQuantity() - quantity);
                break;
            }
        }
    }

    private void cancelElectronicReservation(Set<ProductAvailabilityElectronic> availabilities, Integer quantity, String sku) {
        for (ProductAvailabilityElectronic availability : availabilities) {
            if (availability.getSku().equals(sku)) {
                availability.setReservedQuantity(availability.getReservedQuantity() - quantity);
                break;
            }
        }
    }

    public void confirmReservation(List<Reservation> reservations, List<Product> products) {
        Map<String, Reservation> reservationMap = new HashMap<>();

        reservations.forEach(reservation -> reservationMap.put(reservation.getSku(), reservation));

        products.forEach(product -> {
            if (ProductCategory.BOOKS.equals(product.getCategory())) {
                checkAndConfirmBookReservation(product.getBook().getAvailability(), reservationMap);
            } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
                checkAndConfirmElectronicReservation(product.getElectronic().getAvailability(), reservationMap);
            } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
                checkAndConfirmClothesReservation(product.getClothes().getAvailability(), reservationMap);
            } else {
                checkAndConfirmShoesReservation(product.getShoes().getAvailability(), reservationMap);
            }
        });
    }

    private void checkAndConfirmShoesReservation(Set<ProductAvailabilityShoe> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityShoe availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToFinished(reservationMap, availability);
            }
        }
    }

    private void checkAndConfirmClothesReservation(Set<ProductAvailabilityClothes> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityClothes availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToFinished(reservationMap, availability);
            }
        }
    }

    private void checkAndConfirmElectronicReservation(Set<ProductAvailabilityElectronic> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityElectronic availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToFinished(reservationMap, availability);
            }
        }
    }

    private void checkAndConfirmBookReservation(ProductAvailability availability, Map<String, Reservation> reservationMap) {
        if (reservationMap.containsKey(availability.getSku())) {
            setReservationToFinished(reservationMap, availability);
        }
    }

    public void cancelReservation(List<Reservation> reservations, List<Product> products) {
        Map<String, Reservation> reservationMap = new HashMap<>();

        reservations.forEach(reservation -> reservationMap.put(reservation.getSku(), reservation));

        products.forEach(product -> {
            if (ProductCategory.BOOKS.equals(product.getCategory())) {
                checkAndCancelBookReservation(product.getBook().getAvailability(), reservationMap);
            } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
                checkAndCancelElectronicReservation(product.getElectronic().getAvailability(), reservationMap);
            } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
                checkAndCancelClothesReservation(product.getClothes().getAvailability(), reservationMap);
            } else {
                checkAndCancelShoesReservation(product.getShoes().getAvailability(), reservationMap);
            }
        });
    }

    private void checkAndCancelShoesReservation(Set<ProductAvailabilityShoe> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityShoe availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToCanceled(reservationMap, availability);
            }
        }
    }

    private void checkAndCancelClothesReservation(Set<ProductAvailabilityClothes> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityClothes availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToCanceled(reservationMap, availability);
            }
        }
    }

    private void checkAndCancelElectronicReservation(Set<ProductAvailabilityElectronic> availabilities, Map<String, Reservation> reservationMap) {
        for (ProductAvailabilityElectronic availability : availabilities) {
            if (reservationMap.containsKey(availability.getSku())) {
                setReservationToCanceled(reservationMap, availability);
            }
        }
    }

    private static void setReservationToFinished(Map<String, Reservation> reservationMap, ProductAvailability availability) {
        Integer reservedQuantity = reservationMap.get(availability.getSku()).getQuantity();

        if (availability.getQuantity() >= reservedQuantity) {
            reservationMap.get(availability.getSku()).setStatus(ReservationStatus.FINISHED);

            availability.setQuantity(availability.getQuantity() - reservedQuantity);
        } else {
            reservationMap.get(availability.getSku()).setStatus(ReservationStatus.STOCKOUT);
        }

        availability.setReservedQuantity(availability.getReservedQuantity() - reservedQuantity);
    }

    private static void setReservationToCanceled(Map<String, Reservation> reservationMap, ProductAvailability availability) {
        reservationMap.get(availability.getSku()).setStatus(ReservationStatus.CANCELED);

        availability.setReservedQuantity(
                availability.getReservedQuantity() - reservationMap.get(availability.getSku()).getQuantity());
    }

    private void checkAndCancelBookReservation(ProductAvailability availability, Map<String, Reservation> reservationMap) {
        if (reservationMap.containsKey(availability.getSku())) {
            setReservationToCanceled(reservationMap, availability);
        }
    }
}
