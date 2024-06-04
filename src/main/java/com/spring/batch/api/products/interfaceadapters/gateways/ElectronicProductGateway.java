package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ElectronicProductGateway extends GenericProductGateway {

    public Product findBySku(List<String> skus) {
        if (skus == null || skus.isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findByElectronicAvailabilitySkuIn(skus);
    }

    @Override
    public Product findBySku(String sku) {
        return findBySkuOptional(sku)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Product> findBySkuOptional(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findByElectronicAvailabilitySkuEquals(sku);
    }
}
