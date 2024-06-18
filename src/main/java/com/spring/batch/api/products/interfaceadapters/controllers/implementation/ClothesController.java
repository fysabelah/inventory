package com.spring.batch.api.products.interfaceadapters.controllers.implementation;

import com.spring.batch.api.products.interfaceadapters.controllers.interfaces.ClothesControllerInterface;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ClothesController implements ClothesControllerInterface {
    @Override
    public ProductDto insertClothes(ProductDto body) {
        return null;
    }
}
