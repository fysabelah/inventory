package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductBusinessTest extends TestUtils {

    private ProductBusiness business;

    public ProductBusinessTest() {
        Clock clock = Clock.fixed(
                Instant.parse("2024-07-01T08:40:00Z"),
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

        String expected = ProductCategory.ELECTRONICS.name() + "-" + model.toLowerCase() + "-" + brand.toLowerCase() + "-"
                + type.toLowerCase() + "-" + color.toLowerCase();

        assertEquals(expected, business.createSku(List.of(model, brand, type, color), ProductCategory.ELECTRONICS));
    }

    @Test
    void updateToInsert() {
    }

    @Test
    void updateQuantity() {
    }

    @Test
    void checkIfUpdateValue() {
    }
}