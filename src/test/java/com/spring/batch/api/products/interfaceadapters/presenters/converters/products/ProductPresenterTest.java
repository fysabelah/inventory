package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ClothesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ElectronicAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ProductAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ShoesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

class ProductPresenterTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/interfaceadapters/presenters/converters/products";

    private final ProductPresenter presenter;

    ProductPresenterTest() {
        this.presenter = new ProductPresenter(
                new BookPresenter(new ProductAvailabilityPresenter()),
                new ClothesPresenter(new ClothesAvailabilityPresenter()),
                new ElectronicPresenter(new ElectronicAvailabilityPresenter()),
                new ShoePresenter(new ShoesAvailabilityPresenter()));
    }

    void convertDtoToClass(String fileToConvert, String fileResult) throws IOException, JSONException {
        ProductDto dto = objectMapper.readValue(getMock(BASE_PATH + fileToConvert), ProductDto.class);

        Product shouldBe = presenter.convert(dto);

        Product expected = objectMapper.readValue(getMock(BASE_PATH + fileResult), Product.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }

    void convertClassToDto(String fileToConvert, String fileResult) throws IOException, JSONException {
        Product product = objectMapper.readValue(getMock(BASE_PATH + fileToConvert), Product.class);

        ProductDto shouldBe = presenter.convert(product);

        ProductDto expected = objectMapper.readValue(getMock(BASE_PATH + fileResult), ProductDto.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }


    @Test
    void testBookConversionDtoToClass() throws IOException, JSONException {
        convertDtoToClass("/book/dto.json", "/book/book.json");
    }

    @Test
    void testBookConversionClassToDto() throws IOException, JSONException {
        convertClassToDto("/book/book.json", "/book/dto.json");
    }

    @Test
    void testClothesClassToDto() throws IOException, JSONException {
        convertClassToDto("/clothes/clothes.json", "/clothes/dto.json");
    }

    @Test
    void testClothesDtoClass() throws IOException, JSONException {
        convertDtoToClass("/clothes/dto.json", "/clothes/clothes.json");
    }

    @Test
    void testElectronicClassToDto() throws IOException, JSONException {
        convertClassToDto("/electronic/electronic.json", "/electronic/dto.json");
    }

    @Test
    void testElectronicDtoClass() throws IOException, JSONException {
        convertDtoToClass("/electronic/dto.json", "/electronic/electronic.json");
    }

    @Test
    void testShoesClassToDto() throws IOException, JSONException {
        convertClassToDto("/shoes/shoes.json", "/shoes/dto.json");
    }

    @Test
    void testShoesDtoClass() throws IOException, JSONException {
        convertDtoToClass("/shoes/dto.json", "/shoes/shoes.json");
    }
}