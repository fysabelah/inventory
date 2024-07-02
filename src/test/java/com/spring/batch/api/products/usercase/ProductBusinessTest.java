package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductBusinessTest extends TestUtils {

    private final ProductBusiness business;

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/usercase/mocks/products";

    public ProductBusinessTest() {
        Clock clock = Clock.fixed(
                Instant.parse("2024-07-01T08:40:00.000-03:00"),
                ZoneId.of("America/Sao_Paulo")
        );

        this.business = new ProductBusiness(clock);
    }

    @Test
    void createBookSku() {
        String isbn = "9783127323207";

        String shouldBe = business.createSku(isbn, ProductCategory.BOOKS);

        assertEquals("BOOKS-9783127323207", shouldBe);
    }

    @Test
    void testCreateSku() {
        String model = "S23";
        String brand = "Samsung";
        String type = ElectronicType.CELL_PHONE.name();
        String color = "pink";

        String expected = ProductCategory.ELECTRONICS.name() + "-" + model.toUpperCase() + "-" + brand.toUpperCase() + "-"
                + type.toUpperCase() + "-" + color.toUpperCase();

        assertEquals(expected, business.createSku(List.of(model, brand, type, color), ProductCategory.ELECTRONICS));
    }

    private void updateToInsert(String pathShouldBeFile, String pathExpectedFile) throws IOException, JSONException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + pathShouldBeFile), Product.class);

        business.updateToInsert(product);

        Product expected = objectMapper.readValue(getMock(BASE_PATH + pathExpectedFile), Product.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(product),
                false);
    }

    @Test
    void updateToInsertBook() throws IOException, JSONException {
        updateToInsert("/book/payload.json", "/book/book.json");
    }

    @Test
    void updateToInsertClothes() throws IOException, JSONException {
        updateToInsert("/clothes/payload.json", "/clothes/clothes.json");
    }

    @Test
    void updateToInsertElectronic() throws IOException, JSONException {
        updateToInsert("/electronic/payload.json", "/electronic/electronic.json");
    }

    @Test
    void updateToInsertShoes() throws IOException, JSONException {
        updateToInsert("/shoes/payload.json", "/shoes/shoes.json");
    }

    @Test
    void updateToInsertClothesDuplicate(){

    }

    @Test
    void updateQuantity() {
    }

    @Test
    void checkIfUpdateValue() {
    }
}