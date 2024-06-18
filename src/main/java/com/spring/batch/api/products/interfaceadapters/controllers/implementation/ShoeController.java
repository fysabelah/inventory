package com.spring.batch.api.products.interfaceadapters.controllers.implementation;

import com.spring.batch.api.products.interfaceadapters.controllers.interfaces.ShoesControllerInterface;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ShoeController implements ShoesControllerInterface {

    @Override
    public ProductDto insertShoes(ProductDto body) {
        return null;
    }
}
