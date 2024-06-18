package com.spring.batch.api.products.interfaceadapters.gateways;

import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.frameworks.db.ElectronicRepository;
import com.spring.batch.api.products.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ElectronicGateway {

    private final ElectronicRepository repository;

    public ElectronicGateway(ElectronicRepository repository) {
        this.repository = repository;
    }

    public Electronic findBySku(String sku) {
        return findBySkuOptional(sku)
                .orElseThrow(() -> new NoSuchElementException(MessageUtil.getMessage("NOT_FOUND")));
    }

    public Optional<Electronic> findBySkuOptional(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("MISSING_PARAMETERS"));
        }

        return repository.findByAvailabilitySkuEquals(sku);
    }

    public List<Electronic> findBySku(List<String> skus) {
        return repository.findByAvailabilitySkuIn(skus);
    }

    public Electronic insert(Electronic electronic) {
        return repository.insert(electronic);
    }
}
