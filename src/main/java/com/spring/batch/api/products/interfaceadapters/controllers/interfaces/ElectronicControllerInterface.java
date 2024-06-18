package com.spring.batch.api.products.interfaceadapters.controllers.interfaces;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.exceptions.BusinessException;

public interface ElectronicControllerInterface {

    ProductDto insertElectronic(ProductDto body) throws BusinessException;
}
