package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class ProductBusiness {

    protected final Clock clock;

    protected ProductBusiness(Clock clock) {
        this.clock = clock;
    }

    public String getSku(List<String> fields, ProductCategory category) {
        List<String> normalized = new ArrayList<>();

        for (String field : fields) {
            normalized.add(normalize(field));
        }

        return String.join("-", normalized);
    }

    private String normalize(String value) {
        return value.replaceAll("\\s", "-");
    }

    protected String createSku(List<String> fields, ProductCategory category) {
        List<String> normalized = new ArrayList<>();

        for (String field : fields) {
            normalized.add(normalize(field));
        }

        return category.name().concat(String.join("-", normalized));
    }

    protected String createSku(String field, ProductCategory category) {
        return category.name().concat(normalize(field));
    }

    /* private String createShoesSku(Shoe shoe) {
         return normalize(shoe.getName())
                 .concat("-")
                 .concat(normalize(shoe.getBrand()))
                 .concat("-")
                 .concat(normalize(shoe.getColor()))
                 .concat("-")
                 .concat(normalize(shoe.getSize()));
     }

     private String createClothesSku(Clothes clothes) {
         return normalize(clothes.getName())
                 .concat("-")
                 .concat(normalize(clothes.getModel()))
                 .concat("-")
                 .concat(normalize(clothes.getBrand()))
                 .concat("-")
                 .concat(normalize(clothes.getSize().name()));
     }
 */

    protected void checkIfShouldUpdateQuantity(LocalDateTime lastUpdated, LocalDateTime updatedAt, Integer newQuantity, Integer protection) throws BusinessException {
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

    public void updateProductInformation(Product product, ProductDto productDto) {
        product.setActive(productDto.isActive());
        product.setDescription(productDto.getDescription());
        product.setValue(productDto.getValue());
    }

    public abstract void updateQuantity(Integer quantity, Integer protection, LocalDateTime updatedAt, Product product) throws BusinessException;

    public abstract void updateSpecificInformation(Product toUpdate, Product newInformation) throws BusinessException;
}
