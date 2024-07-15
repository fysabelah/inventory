package com.spring.batch.api.products.usercase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.enums.ReservationStatus;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationBusinessTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/usercase/mocks/reservation";

    private final ReservationBusiness business;

    public ReservationBusinessTest() {
        this.business = new ReservationBusiness(super.clock);
    }

    private List<Product> getProducts(String path) throws IOException {
        return super.objectMapper.readValue(super.getMock(BASE_PATH + path), new TypeReference<List<Product>>() {
        });
    }

    private List<Reservation> getReservations(String path) throws IOException {
        return super.objectMapper.readValue(super.getMock(BASE_PATH + path), new TypeReference<List<Reservation>>() {
        });
    }

    private void create(String pathProducts, String pathProductsExpected, String pathReservations, String pathReservationsExpected) throws IOException, BusinessException, JSONException {
        List<Product> products = getProducts(pathProducts);

        List<Reservation> reservations = getReservations(pathReservations);

        business.create(reservations, products);

        List<Reservation> expectedReservation = getReservations(pathReservationsExpected);

        List<Product> expectedProducts = getProducts(pathProductsExpected);

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedProducts),
                objectMapper.writeValueAsString(products)
        );

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservations)
        );
    }

    @Test
    void createSuccess() throws IOException, BusinessException, JSONException {
        create("/success_creation/products.json",
                "/success_creation/products_expected.json",
                "/success_creation/reservations.json",
                "/success_creation/reservations_expected.json"
        );
    }

    @Test
    void createMissingSkuThrowBusinessException() throws IOException {
        try {
            List<Product> products = getProducts("/missing_sku_creation/products.json");

            List<Reservation> reservations = getReservations("/missing_sku_creation/reservations.json");

            business.create(reservations, products);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("SKU_NOT_EXISTS", "BOOKS-78598-9832"), exception.getMessage());
        }
    }

    @Test
    void createWithStockoutAndSuccess() throws IOException, BusinessException, JSONException {
        create("/with_stockout_creation/products.json",
                "/with_stockout_creation/products_expected.json",
                "/with_stockout_creation/reservations.json",
                "/with_stockout_creation/reservations_expected.json");
    }

    @Test
    void createWithAllStockOut() throws IOException, BusinessException, JSONException {
        create("/all_stockout_creation/products.json",
                "/all_stockout_creation/products_expected.json",
                "/all_stockout_creation/reservations.json",
                "/all_stockout_creation/reservations_expected.json");
    }

    @Test
    void updateQuantityThrowSameQuantity() throws IOException {
        try {
            Product product = getProducts("/update_quantity/book/products.json").get(0);

            Reservation reservation = getReservations("/update_quantity/book/reservations.json").get(0);

            business.updateReservationQuantity(reservation, product, 2);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("SAME_QUANTITY_FOR_RESERVATION"), exception.getMessage());
        }
    }

    @Test
    void updateQuantityThrowStatusNotAllowed() throws IOException {
        try {
            Product product = getProducts("/update_quantity/book/products.json").get(0);

            Reservation reservation = getReservations("/update_quantity/book/reservations.json").get(0);
            reservation.setStatus(ReservationStatus.STOCKOUT);

            business.updateReservationQuantity(reservation, product, 5);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("RESERVATION_CANT_BE_UPDATED_ON_STATUS"), exception.getMessage());
        }
    }

    @Test
    void updateReservationQuantityBookSuccess() throws IOException, BusinessException, JSONException {
        updateQuantity("/update_quantity/book/products.json",
                "/update_quantity/book/products_expected.json",
                "/update_quantity/book/reservations.json",
                "/update_quantity/book/reservations_expected.json",
                3
        );
    }

    @Test
    void updateReservationQuantityBookThrowStockOut() throws IOException {
        try {
            Product product = getProducts("/update_quantity/book/products.json").get(0);

            Reservation reservation = getReservations("/update_quantity/book/reservations.json").get(0);

            business.updateReservationQuantity(reservation, product, 5);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("RESERVATION_QUANTITY_UNAVAILABLE"), exception.getMessage());
        }
    }

    private void updateQuantity(String productPath, String productExpectedPath, String reservationPath,
                                String reservationPathExpected, int quantity) throws IOException, BusinessException, JSONException {
        Product product = getProducts(productPath).get(0);

        Reservation reservation = getReservations(reservationPath).get(0);

        business.updateReservationQuantity(reservation, product, quantity);

        Reservation expectedReservation = getReservations(reservationPathExpected).get(0);

        Product expectedProduct = getProducts(productExpectedPath).get(0);

        super.assertJsonEquals(objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservation));

        super.assertJsonEquals(objectMapper.writeValueAsString(expectedProduct),
                objectMapper.writeValueAsString(product));
    }

    @Test
    void updateReservationQuantityClothesCanceled() throws IOException, BusinessException, JSONException {
        updateQuantity("/update_quantity/clothes/products.json",
                "/update_quantity/clothes/products_expected.json",
                "/update_quantity/clothes/reservations.json",
                "/update_quantity/clothes/reservations_expected.json",
                0
        );
    }

    @Test
    void confirmReservation() throws IOException, JSONException {
        List<Product> products = getProducts("/confirm/products.json");

        List<Reservation> reservations = getReservations("/confirm/reservations.json");

        business.confirmReservation(reservations, products);

        List<Reservation> expectedReservation = getReservations("/confirm/reservations_expected.json");

        List<Product> expectedProducts = getProducts("/confirm/products_expected.json");

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedProducts),
                objectMapper.writeValueAsString(products)
        );

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservations)
        );
    }

    @Test
    void cancelReservation() throws IOException, JSONException {
        List<Product> products = getProducts("/cancel/products.json");

        List<Reservation> reservations = getReservations("/cancel/reservations.json");

        business.cancelReservation(reservations, products);

        List<Reservation> expectedReservation = getReservations("/cancel/reservations_expected.json");

        List<Product> expectedProducts = getProducts("/cancel/products_expected.json");

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedProducts),
                objectMapper.writeValueAsString(products)
        );

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservations)
        );
    }
}