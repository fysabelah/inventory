package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.batch.api.products.TestUtils;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.AvailabilityDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ClothesDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ElectronicDto;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ShoeDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PresenterAvailabilityTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/batch/api/products/interfaceadapters/presenters/converters/availability";

    @Test
    void basicConversionDtoToClass() throws IOException {
        ProductAvailabilityPresenter presenter = new ProductAvailabilityPresenter();

        AvailabilityDto dto = objectMapper.readValue(getMock(BASE_PATH + "/basic/availability_dto.json"), AvailabilityDto.class);

        ProductAvailability availability = presenter.convert(dto);

        ProductAvailability shouldBe = objectMapper.readValue(getMock(BASE_PATH + "/basic/product_availability.json"), ProductAvailability.class);

        assertThat(availability)
                .usingRecursiveComparison()
                .isEqualTo(shouldBe);
    }

    @Test
    void basicConversionClassToDto() throws IOException {
        ProductAvailabilityPresenter presenter = new ProductAvailabilityPresenter();

        ProductAvailability productAvailability = objectMapper.readValue(getMock(BASE_PATH + "/basic/product_availability.json"), ProductAvailability.class);

        AvailabilityDto shouldBe = presenter.convert(productAvailability);

        AvailabilityDto expected = objectMapper.readValue(getMock(BASE_PATH + "/basic/availability_dto.json"), AvailabilityDto.class);

        assertThat(shouldBe)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void testClothesClassToDto() throws IOException, JSONException {
        ClothesAvailabilityPresenter presenter = new ClothesAvailabilityPresenter();

        Set<ProductAvailabilityClothes> clothes = objectMapper.readValue(
                getMock(BASE_PATH + "/clothes/clothes_availability.json"),
                new TypeReference<Set<ProductAvailabilityClothes>>() {
                }
        );

        List<ClothesDto.ClothesAvailabilityDto> shouldBe = presenter.convert(clothes);

        List<ClothesDto.ClothesAvailabilityDto> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/clothes/clothes_dto.json"),
                new TypeReference<List<ClothesDto.ClothesAvailabilityDto>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testClothesDtoClass() throws IOException, JSONException {
        ClothesAvailabilityPresenter presenter = new ClothesAvailabilityPresenter();

        List<ClothesDto.ClothesAvailabilityDto> availabilityDtos = objectMapper.readValue(
                getMock(BASE_PATH + "/clothes/clothes_dto.json"),
                new TypeReference<List<ClothesDto.ClothesAvailabilityDto>>() {
                }
        );

        Set<ProductAvailabilityClothes> shouldBe = presenter.convert(availabilityDtos);

        Set<ProductAvailabilityClothes> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/clothes/clothes_availability.json"),
                new TypeReference<Set<ProductAvailabilityClothes>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testElectronicClassToDto() throws IOException, JSONException {
        ElectronicAvailabilityPresenter presenter = new ElectronicAvailabilityPresenter();

        Set<ProductAvailabilityElectronic> availability = objectMapper.readValue(
                getMock(BASE_PATH + "/electronic/electronic_availability.json"),
                new TypeReference<Set<ProductAvailabilityElectronic>>() {
                }
        );

        List<ElectronicDto.ElectronicAvailabilityDto> shouldBe = presenter.convert(availability);

        List<ElectronicDto.ElectronicAvailabilityDto> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/electronic/electronic_dto.json"),
                new TypeReference<List<ElectronicDto.ElectronicAvailabilityDto>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testElectronicDtoClass() throws IOException, JSONException {
        ElectronicAvailabilityPresenter presenter = new ElectronicAvailabilityPresenter();

        List<ElectronicDto.ElectronicAvailabilityDto> availability = objectMapper.readValue(
                getMock(BASE_PATH + "/electronic/electronic_dto.json"),
                new TypeReference<List<ElectronicDto.ElectronicAvailabilityDto>>() {
                }
        );

        Set<ProductAvailabilityElectronic> shouldBe = presenter.convert(availability);

        Set<ProductAvailabilityElectronic> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/electronic/electronic_availability.json"),
                new TypeReference<Set<ProductAvailabilityElectronic>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testShoesClassToDto() throws IOException, JSONException {
        ShoesAvailabilityPresenter presenter = new ShoesAvailabilityPresenter();

        Set<ProductAvailabilityShoe> availability = objectMapper.readValue(
                getMock(BASE_PATH + "/shoes/shoes_availability.json"),
                new TypeReference<Set<ProductAvailabilityShoe>>() {
                }
        );

        List<ShoeDto.ShoeAvailabilityDto> shouldBe = presenter.convert(availability);

        List<ShoeDto.ShoeAvailabilityDto> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/shoes/shoes_dto.json"),
                new TypeReference<List<ShoeDto.ShoeAvailabilityDto>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }

    @Test
    void testShoesDtoClass() throws IOException, JSONException {
        ShoesAvailabilityPresenter presenter = new ShoesAvailabilityPresenter();

        List<ShoeDto.ShoeAvailabilityDto> availability = objectMapper.readValue(
                getMock(BASE_PATH + "/shoes/shoes_dto.json"),
                new TypeReference<List<ShoeDto.ShoeAvailabilityDto>>() {
                }
        );

        Set<ProductAvailabilityShoe> shouldBe = presenter.convert(availability);

        Set<ProductAvailabilityShoe> expected = objectMapper.readValue(
                getMock(BASE_PATH + "/shoes/shoes_availability.json"),
                new TypeReference<Set<ProductAvailabilityShoe>>() {
                }
        );

        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(shouldBe), false);
    }
}