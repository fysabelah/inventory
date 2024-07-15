package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.*;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityClothes;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityShoe;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ProductBusiness {

    protected final Clock clock;

    protected ProductBusiness(Clock clock) {
        this.clock = clock;
    }

    private String normalize(String value) {
        return value.replaceAll("\\s", "-")
                .toUpperCase();
    }

    protected String createSku(List<String> fields, ProductCategory category) {
        List<String> normalized = new ArrayList<>();

        for (String field : fields) {
            normalized.add(normalize(field));
        }

        return category.name().concat("-").concat(String.join("-", normalized));
    }

    protected String createSku(String field) {
        return ProductCategory.BOOKS.name().concat("-").concat(normalize(field));
    }

    private void checkIfShouldUpdateQuantity(LocalDateTime lastUpdated, LocalDateTime updatedAt, Integer newQuantity, Integer protection) throws BusinessException {
        if (lastUpdated.isAfter(updatedAt)) {
            throw new BusinessException("PRODUCT_HAS_LATEST_UPDATE");
        }

        if (newQuantity < 0) {
            throw new BusinessException("AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE");
        }

        if (protection < 0) {
            throw new BusinessException("PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE");
        }
    }

    public void updateToInsert(Product product) {
        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            updateToInsert(product.getBook());
        } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
            updateToInsert(product.getElectronic());
        } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            updateToInsert(product.getClothes());
        } else {
            updateToInsert(product.getShoes());
        }
    }

    private void updateToInsert(Shoes shoes) {
        String name = shoes.getName();
        String brand = shoes.getBrand();
        String color = shoes.getColor();

        Map<String, ProductAvailabilityShoe> availabilityClothesMap = new HashMap<>();

        for (ProductAvailabilityShoe availability : shoes.getAvailability()) {
            String sku = createSku(
                    List.of(name, brand, color, availability.getSize()),
                    ProductCategory.SHOES
            );

            updateBaseAvailabilityInformation(sku, availability);

            availabilityClothesMap.put(sku, availability);
        }

        shoes.setAvailability(new HashSet<>(availabilityClothesMap.values()));
    }

    private void updateBaseAvailabilityInformation(String sku, ProductAvailability availability) {
        availability.setUpdatedAt(LocalDateTime.now(clock));
        availability.setSku(sku);
        availability.setReservedQuantity(0);
    }

    private void updateToInsert(Book book) {
        updateBaseAvailabilityInformation(createSku(book.getIsbn()), book.getAvailability());
    }

    private void updateToInsert(Electronic electronic) {
        String model = electronic.getModel();
        String brand = electronic.getBrand();
        ElectronicType type = electronic.getType();

        Map<String, ProductAvailabilityElectronic> availabilityClothesMap = new HashMap<>();

        for (ProductAvailabilityElectronic availability : electronic.getAvailability()) {
            String sku = createSku(List.of(model, brand, type.name(), availability.getColor()),
                    ProductCategory.ELECTRONICS);

            updateBaseAvailabilityInformation(sku, availability);

            availabilityClothesMap.put(sku, availability);
        }

        electronic.setAvailability(new HashSet<>(availabilityClothesMap.values()));
    }

    private void updateToInsert(Clothes clothes) {
        String model = clothes.getModel();
        String brand = clothes.getBrand();
        String color = clothes.getColor();

        Map<String, ProductAvailabilityClothes> availabilityClothesMap = new HashMap<>();

        for (ProductAvailabilityClothes availability : clothes.getAvailability()) {
            String sku = createSku(List.of(model, brand, color, availability.getSize().name()), ProductCategory.CLOTHES);

            updateBaseAvailabilityInformation(sku, availability);

            availabilityClothesMap.put(sku, availability);
        }

        clothes.setAvailability(new HashSet<>(availabilityClothesMap.values()));
    }

    public void updateQuantity(Integer quantity, LocalDateTime updatedAt, Integer protection, String sku, Product product) throws BusinessException {
        Optional<ProductAvailability> availability = product.getAvailabilities()
                .stream()
                .filter(productAvailability -> productAvailability.getSku().equals(sku))
                .findFirst();

        if (availability.isEmpty()) {
            throw new BusinessException("NOT_FOUND");
        }

        ProductAvailability toUpdate = availability.get();

        checkIfShouldUpdateQuantity(toUpdate.getUpdatedAt(), updatedAt, quantity, protection);

        toUpdate.setQuantity(quantity);
        toUpdate.setUpdatedAt(updatedAt);
        toUpdate.setProtection(protection);
    }

    public void checkIfUpdateValue(Product product, BigDecimal value) throws BusinessException {
        if (BigDecimal.ZERO.compareTo(value) > 0) {
            throw new BusinessException("VALUE_CANT_BE_NEGATIVE");
        }

        product.setValue(value);
    }
}
