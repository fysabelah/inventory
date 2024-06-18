package com.spring.batch.api.products.interfaceadapters.controllers.implementation;

import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.interfaceadapters.controllers.interfaces.ElectronicControllerInterface;
import com.spring.batch.api.products.interfaceadapters.gateways.ElectronicGateway;
import com.spring.batch.api.products.interfaceadapters.presenters.converters.products.ElectronicPresenter;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.usercase.ElectronicBusiness;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElectronicController implements ElectronicControllerInterface {

    private final ElectronicPresenter presenter;

    private final ElectronicGateway gateway;

    private final ElectronicBusiness business;

    public ElectronicController(ElectronicPresenter presenter, ElectronicGateway gateway, ElectronicBusiness business) {
        this.presenter = presenter;
        this.gateway = gateway;
        this.business = business;
    }

    @Override
    public ProductDto insertElectronic(ProductDto body) throws BusinessException {
        Electronic electronic = presenter.convert(body);

        business.updateToInsert(electronic);

        List<String> skus = electronic.getAvailability()
                .stream()
                .map(ProductAvailability::getSku)
                .toList();

        List<Electronic> saved = gateway.findBySku(skus);

        if (saved != null) {
            throw new BusinessException("PRODUCT_ALREADY_REGISTERED");
        }

        electronic = gateway.insert(electronic);

        return presenter.convert(electronic);
    }
}
