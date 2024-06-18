package com.spring.batch.api.products.interfaceadapters.controllers.interfaces;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;

public interface ShoesControllerInterface {

    ProductDto insertShoes(ProductDto body);
}
