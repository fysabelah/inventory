package com.spring.batch.api.products.interfaceadapters.controllers.interfaces;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.exceptions.BusinessException;

public interface BookControllerInterface {

    ProductDto insertBook(ProductDto body) throws BusinessException;
}
