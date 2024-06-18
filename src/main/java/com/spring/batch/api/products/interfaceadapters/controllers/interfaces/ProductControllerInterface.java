package com.spring.batch.api.products.interfaceadapters.controllers.interfaces;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.MessageUtil;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;

public interface ProductControllerInterface extends BookControllerInterface, ElectronicControllerInterface,
        ShoesControllerInterface, ClothesControllerInterface {

    default ProductDto insert(ProductDto body) throws NoSuchMethodException, BusinessException {
        if (ProductCategory.BOOKS.equals(body.getCategory())) {
            return insertBook(body);
        }

        if (ProductCategory.ELECTRONICS.equals(body.getCategory())) {
            return insertElectronic(body);
        }

        if (ProductCategory.SHOES.equals(body.getCategory())) {
            return insertShoes(body);
        }

        if (ProductCategory.CLOTHES.equals(body.getCategory())) {
            return insertClothes(body);
        }

        throw new NoSuchMethodException(MessageUtil.getMessage("METHOD_NOT_IMPLEMENTED"));
    }
}
