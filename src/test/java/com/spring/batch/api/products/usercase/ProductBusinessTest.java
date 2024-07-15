package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductBusinessTest extends TestUtils {

    private final ProductBusiness business;

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/usercase/mocks/products";

    public ProductBusinessTest() {
        this.business = new ProductBusiness(super.clock);
    }

    @Test
    void createBookSku() {
        String isbn = "9783127323207";

        String shouldBe = business.createSku(isbn);

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

        super.assertJsonEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(product)
        );
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
    void updateToInsertClothesDuplicate() throws JSONException, IOException {
        updateToInsert("/clothes/payload-duplicate.json", "/clothes/clothes.json");
    }

    @Test
    void updateToInsertElectronicDuplicate() throws JSONException, IOException {
        updateToInsert("/electronic/payload-duplicate.json", "/electronic/electronic.json");
    }

    @Test
    void updateToInsertShoesDuplicate() throws JSONException, IOException {
        updateToInsert("/shoes/payload-duplicate.json", "/shoes/shoes.json");
    }

    private void updateQuantityThrow(String path, String sku) throws IOException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + path), Product.class);

        assertThrows(BusinessException.class, () -> business.updateQuantity(
                3,
                LocalDateTime.now(super.clock),
                1,
                sku,
                product
        ));
    }

    @Test
    void updateQuantityThrows() throws IOException {
        updateQuantityThrow("/clothes/clothes.json", "CLOTHES-SOME-JEANS-JEANS-BRAND-YELLOW-GG");
        updateQuantityThrow("/electronic/electronic.json", "ELECTRONICS-SOME-TV-TV-BRAND-TV-BLACK");
        updateQuantityThrow("/shoes/shoes.json", "SHOES-BOOTS-FOR-PRETTY-GIRLS-SHOE-BRAND-BLACK-44");
    }

    @Test
    void updateQuantityBook() throws IOException, BusinessException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Product.class);

        LocalDateTime expectedDate = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        business.updateQuantity(15,
                expectedDate,
                2,
                product.getBook().getAvailability().getSku(),
                product
        );

        assertEquals(15, product.getBook().getAvailability().getQuantity());
        assertEquals(2, product.getBook().getAvailability().getProtection());
        assertEquals(expectedDate, product.getBook().getAvailability().getUpdatedAt());
    }

    @Test
    void updateQuantityElectronic() throws IOException, BusinessException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + "/electronic/electronic.json"), Product.class);

        String sku = "ELECTRONICS-SOME-TV-TV-BRAND-TV-RED";

        LocalDateTime expectedDate = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        business.updateQuantity(45,
                expectedDate,
                2,
                sku,
                product
        );

        ProductAvailabilityElectronic availability = product.getElectronic().getAvailability()
                .stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .get();

        assertEquals(45, availability.getQuantity());
        assertEquals(2, availability.getProtection());
        assertEquals(expectedDate, availability.getUpdatedAt());
    }

    @Test
    void updateQuantityClothes() throws IOException, BusinessException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + "/clothes/clothes.json"), Product.class);

        String sku = "CLOTHES-SOME-JEANS-JEANS-BRAND-YELLOW-P";

        LocalDateTime expectedDate = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        business.updateQuantity(45,
                expectedDate,
                2,
                sku,
                product
        );

        ProductAvailabilityClothes availability = product.getClothes().getAvailability()
                .stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .get();

        assertEquals(45, availability.getQuantity());
        assertEquals(2, availability.getProtection());
        assertEquals(expectedDate, availability.getUpdatedAt());
    }

    @Test
    void updateQuantityShoes() throws IOException, BusinessException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + "/shoes/shoes.json"), Product.class);

        String sku = "SHOES-BOOTS-FOR-PRETTY-GIRLS-SHOE-BRAND-BLACK-38";

        LocalDateTime expectedDate = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        business.updateQuantity(45,
                expectedDate,
                2,
                sku,
                product
        );

        ProductAvailabilityShoe availability = product.getShoes().getAvailability()
                .stream()
                .filter(item -> item.getSku().equals(sku))
                .findFirst()
                .get();

        assertEquals(45, availability.getQuantity());
        assertEquals(2, availability.getProtection());
        assertEquals(expectedDate, availability.getUpdatedAt());
    }

    @Test
    void updateQuantityValidateAfterDate() throws IOException {
        try {
            Product product = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Product.class);

            product.getBook().getAvailability().setUpdatedAt(LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

            business.updateQuantity(15,
                    LocalDateTime.now(super.clock),
                    6,
                    product.getBook().getAvailability().getSku(),
                    product);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("PRODUCT_HAS_LATEST_UPDATE"), exception.getMessage());
        }
    }

    @Test
    void updateQuantityValidateNegativeQuantity() throws IOException {
        try {
            Product product = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Product.class);

            LocalDateTime date = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            business.updateQuantity(-8,
                    date,
                    6,
                    product.getBook().getAvailability().getSku(),
                    product);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE"), exception.getMessage());
        }
    }

    @Test
    void updateQuantityValidateNegativeProtection() throws IOException {
        try {
            Product product = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Product.class);

            LocalDateTime date = LocalDateTime.parse("2024-07-03T19:59:00.000-03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            business.updateQuantity(26,
                    date,
                    -6,
                    product.getBook().getAvailability().getSku(),
                    product);
        } catch (BusinessException exception) {
            assertEquals(MessageUtil.getMessage("PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE"), exception.getMessage());
        }
    }

    @Test
    void checkIfUpdateValueThrow() {
        assertThrows(BusinessException.class, () -> business.checkIfUpdateValue(new Product(), new BigDecimal("-1")));
    }

    @Test
    void checkIfUpdateValue() {
        Product product = new Product();
        product.setValue(BigDecimal.TEN);

        assertDoesNotThrow(() -> business.checkIfUpdateValue(product, BigDecimal.TWO));

        assertEquals(BigDecimal.TWO, product.getValue());
    }
}