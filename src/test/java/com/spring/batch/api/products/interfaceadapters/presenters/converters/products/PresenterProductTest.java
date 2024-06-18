package com.spring.batch.api.products.interfaceadapters.presenters.converters.products;

import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.Book;
import com.spring.batch.api.products.entities.Clothes;
import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.entities.Shoes;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ClothesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ElectronicAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ProductAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.availability.ShoesAvailabilityPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

class PresenterProductTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/interfaceadapters/presenters/converters/products";

    @Test
    void testBookConversionDtoToClass() throws IOException, JSONException {
        BookPresenter presenter = new BookPresenter(new ProductAvailabilityPresenter());

        ProductDto dto = objectMapper.readValue(getMock(BASE_PATH + "/book/dto.json"), ProductDto.class);

        Book shouldBe = presenter.convert(dto);

        Book expected = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Book.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }

    @Test
    void testBookConversionClassToDto() throws IOException, JSONException {
        BookPresenter presenter = new BookPresenter(new ProductAvailabilityPresenter());

        Book product = objectMapper.readValue(getMock(BASE_PATH + "/book/book.json"), Book.class);

        ProductDto shouldBe = presenter.convert(product);

        ProductDto expected = objectMapper.readValue(getMock(BASE_PATH + "/book/dto.json"), ProductDto.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }

    @Test
    void testClothesClassToDto() throws IOException, JSONException {
        ClothesPresenter presenter = new ClothesPresenter(new ClothesAvailabilityPresenter());

        Clothes product = objectMapper.readValue(getMock(BASE_PATH + "/clothes/clothes.json"), Clothes.class);

        ProductDto shouldBe = presenter.convert(product);

        ProductDto expected = objectMapper.readValue(getMock(BASE_PATH + "/clothes/dto.json"), ProductDto.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testClothesDtoClass() throws IOException, JSONException {
        ClothesPresenter presenter = new ClothesPresenter(new ClothesAvailabilityPresenter());

        ProductDto dto = objectMapper.readValue(getMock(BASE_PATH + "/clothes/dto.json"), ProductDto.class);

        Clothes shouldBe = presenter.convert(dto);

        Clothes expected = objectMapper.readValue(getMock(BASE_PATH + "/clothes/clothes.json"), Clothes.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testElectronicClassToDto() throws IOException, JSONException {
        ElectronicPresenter presenter = new ElectronicPresenter(new ElectronicAvailabilityPresenter());

        Electronic product = objectMapper.readValue(getMock(BASE_PATH + "/electronic/electronic.json"), Electronic.class);

        ProductDto shouldBe = presenter.convert(product);

        ProductDto expected = objectMapper.readValue(getMock(BASE_PATH + "/electronic/dto.json"), ProductDto.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testElectronicDtoClass() throws IOException, JSONException {
        ElectronicPresenter presenter = new ElectronicPresenter(new ElectronicAvailabilityPresenter());

        ProductDto dto = objectMapper.readValue(getMock(BASE_PATH + "/electronic/dto.json"), ProductDto.class);

        Electronic shouldBe = presenter.convert(dto);

        Electronic expected = objectMapper.readValue(getMock(BASE_PATH + "/electronic/electronic.json"), Electronic.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testShoesClassToDto() throws IOException, JSONException {
        ShoePresenter presenter = new ShoePresenter(new ShoesAvailabilityPresenter());

        Shoes product = objectMapper.readValue(getMock(BASE_PATH + "/shoes/shoes.json"), Shoes.class);

        ProductDto shouldBe = presenter.convert(product);

        ProductDto expected = objectMapper.readValue(getMock(BASE_PATH + "/shoes/dto.json"), ProductDto.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testShoesDtoClass() throws IOException, JSONException {
        ShoePresenter presenter = new ShoePresenter(new ShoesAvailabilityPresenter());

        ProductDto dto = objectMapper.readValue(getMock(BASE_PATH + "/shoes/dto.json"), ProductDto.class);

        Shoes shouldBe = presenter.convert(dto);

        Shoes expected = objectMapper.readValue(getMock(BASE_PATH + "/shoes/shoes.json"), Shoes.class);

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }
}