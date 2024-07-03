package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.*;
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
            LocalDateTime updatedAt = LocalDateTime.now(clock);
            String sku = createSku(
                    List.of(name, brand, color, availability.getSize()),
                    ProductCategory.SHOES
            );

            availability.setUpdatedAt(updatedAt);
            availability.setSku(sku);
            availability.setReservedQuantity(0);

            availabilityClothesMap.put(sku, availability);
        }

        shoes.setAvailability(new HashSet<>(availabilityClothesMap.values()));
    }

    private void updateToInsert(Book book) {
        book.getAvailability().setUpdatedAt(LocalDateTime.now(clock));
        book.getAvailability().setSku(createSku(book.getIsbn()));
        book.getAvailability().setReservedQuantity(0);
    }

    private void updateToInsert(Electronic electronic) {
        String model = electronic.getModel();
        String brand = electronic.getBrand();
        ElectronicType type = electronic.getType();

        Map<String, ProductAvailabilityElectronic> availabilityClothesMap = new HashMap<>();

        for (ProductAvailabilityElectronic availability : electronic.getAvailability()) {
            LocalDateTime updatedAt = LocalDateTime.now(clock);
            String sku = createSku(List.of(model, brand, type.name(), availability.getColor()),
                    ProductCategory.ELECTRONICS);

            availability.setUpdatedAt(updatedAt);
            availability.setSku(sku);
            availability.setReservedQuantity(0);

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
            LocalDateTime updatedAt = LocalDateTime.now(clock);
            String sku = createSku(List.of(model, brand, color, availability.getSize().name()), ProductCategory.CLOTHES);

            availability.setUpdatedAt(updatedAt);
            availability.setSku(sku);
            availability.setReservedQuantity(0);

            availabilityClothesMap.put(sku, availability);
        }

        clothes.setAvailability(new HashSet<>(availabilityClothesMap.values()));
    }

    private void updateBookQuantity(Integer quantity, LocalDateTime updatedAt, Integer protection, Book book) throws BusinessException {
        checkIfShouldUpdateQuantity(book.getAvailability().getUpdatedAt(), updatedAt, quantity, protection);

        book.getAvailability().setQuantity(quantity);
        book.getAvailability().setProtection(protection);
        book.getAvailability().setUpdatedAt(updatedAt);
    }

    public void updateQuantity(Integer quantity, LocalDateTime updatedAt, Integer protection, String sku, Product product) throws BusinessException {
        if (ProductCategory.BOOKS.equals(product.getCategory())) {
            updateBookQuantity(quantity, updatedAt, protection, product.getBook());
        } else if (ProductCategory.ELECTRONICS.equals(product.getCategory())) {
            updateElectronicQuantity(sku, quantity, updatedAt, protection, product.getElectronic());
        } else if (ProductCategory.CLOTHES.equals(product.getCategory())) {
            updateClothesQuantity(sku, quantity, updatedAt, protection, product.getClothes());
        } else if (ProductCategory.SHOES.equals(product.getCategory())) {
            updateShoesQuantity(sku, quantity, updatedAt, protection, product.getShoes());
        }
    }

    private void updateShoesQuantity(String sku, Integer quantity, LocalDateTime updatedAt, Integer protection, Shoes shoes) throws BusinessException {
        Optional<ProductAvailabilityShoe> availability = shoes.getAvailability()
                .stream()
                .filter(productAvailabilityShoe -> productAvailabilityShoe.getSku().equals(sku))
                .findAny();

        if (availability.isEmpty()) {
            throw new BusinessException("NOT_FOUND");
        }

        ProductAvailabilityShoe toUpdate = availability.get();

        checkIfShouldUpdateQuantity(toUpdate.getUpdatedAt(), updatedAt, quantity, protection);

        shoes.getAvailability().remove(toUpdate);

        toUpdate.setQuantity(quantity);
        toUpdate.setUpdatedAt(updatedAt);
        toUpdate.setProtection(protection);

        shoes.getAvailability().add(toUpdate);
    }

    private void updateClothesQuantity(String sku, Integer quantity, LocalDateTime updatedAt, Integer protection, Clothes clothes) throws BusinessException {
        Optional<ProductAvailabilityClothes> availability = clothes.getAvailability()
                .stream()
                .filter(productAvailabilityClothes -> productAvailabilityClothes.getSku().equals(sku))
                .findAny();

        if (availability.isEmpty()) {
            throw new BusinessException("NOT_FOUND");
        }

        ProductAvailabilityClothes toUpdate = availability.get();

        checkIfShouldUpdateQuantity(toUpdate.getUpdatedAt(), updatedAt, quantity, protection);

        clothes.getAvailability().remove(toUpdate);

        toUpdate.setQuantity(quantity);
        toUpdate.setUpdatedAt(updatedAt);
        toUpdate.setProtection(protection);

        clothes.getAvailability().add(toUpdate);
    }

    private void updateElectronicQuantity(String sku, Integer quantity, LocalDateTime updatedAt, Integer protection, Electronic electronic) throws BusinessException {
        Optional<ProductAvailabilityElectronic> availability = electronic.getAvailability()
                .stream()
                .filter(productAvailabilityElectronic -> productAvailabilityElectronic.getSku().equals(sku))
                .findAny();

        if (availability.isEmpty()) {
            throw new BusinessException("NOT_FOUND");
        }

        ProductAvailabilityElectronic toUpdate = availability.get();

        checkIfShouldUpdateQuantity(toUpdate.getUpdatedAt(), updatedAt, quantity, protection);

        electronic.getAvailability().remove(toUpdate);

        toUpdate.setQuantity(quantity);
        toUpdate.setUpdatedAt(updatedAt);
        toUpdate.setProtection(protection);

        electronic.getAvailability().add(toUpdate);
    }

    public void checkIfUpdateValue(Product product, BigDecimal value) throws BusinessException {
        if (BigDecimal.ZERO.compareTo(value) > 0) {
            throw new BusinessException("VALUE_CANT_BE_NEGATIVE");
        }

        product.setValue(value);
    }
}
