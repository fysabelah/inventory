package com.spring.batch.api.products.usercase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.Reservation;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationBusinessTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/usercase/mocks/reservation";

    private final ReservationBusiness business;

    public ReservationBusinessTest() {
        this.business = new ReservationBusiness(super.clock);
    }

    @Test
    void createSuccess() throws IOException, BusinessException, JSONException {
        List<Product> products = super.objectMapper.readValue(super.getMock(BASE_PATH + "/success_creation/products.json"), new TypeReference<List<Product>>() {
        });
        List<Reservation> reservations = super.objectMapper.readValue(super.getMock(BASE_PATH + "/success_creation/reservations.json"), new TypeReference<List<Reservation>>() {
        });

        business.create(reservations, products);

        List<Reservation> expectedReservation = super.objectMapper.readValue(super.getMock(BASE_PATH + "/success_creation/reservations_expected.json"), new TypeReference<List<Reservation>>() {
        });

        List<Product> expectedProducts = super.objectMapper.readValue(super.getMock(BASE_PATH + "/success_creation/products_expected.json"), new TypeReference<List<Product>>() {
        });

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expectedProducts),
                objectMapper.writeValueAsString(products),
                false);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservations),
                false);
    }

    @Test
    void createMissingSkuThrowBusinessException() throws IOException {
        try {
            List<Product> products = super.objectMapper.readValue(super.getMock(BASE_PATH + "/missing_sku_creation/products.json"), new TypeReference<List<Product>>() {
            });

            List<Reservation> reservations = super.objectMapper.readValue(super.getMock(BASE_PATH + "/missing_sku_creation/reservations.json"), new TypeReference<List<Reservation>>() {
            });

            business.create(reservations, products);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("SKU_NOT_EXISTS", "BOOKS-78598-9832"), exception.getMessage());
        }
    }

    @Test
    void createWithStockoutAndSuccess() throws IOException, BusinessException, JSONException {
        List<Product> products = super.objectMapper.readValue(super.getMock(BASE_PATH + "/with_stockout_creation/products.json"), new TypeReference<List<Product>>() {
        });
        List<Reservation> reservations = super.objectMapper.readValue(super.getMock(BASE_PATH + "/with_stockout_creation/reservations.json"), new TypeReference<List<Reservation>>() {
        });

        business.create(reservations, products);

        List<Reservation> expectedReservation = super.objectMapper.readValue(super.getMock(BASE_PATH + "/with_stockout_creation/reservations_expected.json"), new TypeReference<List<Reservation>>() {
        });

        List<Product> expectedProducts = super.objectMapper.readValue(super.getMock(BASE_PATH + "/with_stockout_creation/products_expected.json"), new TypeReference<List<Product>>() {
        });

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expectedProducts),
                objectMapper.writeValueAsString(products),
                false);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expectedReservation),
                objectMapper.writeValueAsString(reservations),
                false);
    }

    @Test
    void createWithAllStockout(){

    }

    @Test
    void updateReservationQuantity() {
    }

    @Test
    void confirmReservation() {
    }

    @Test
    void cancelReservation() {
    }
}