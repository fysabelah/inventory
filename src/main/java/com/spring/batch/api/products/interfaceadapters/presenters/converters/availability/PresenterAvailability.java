package com.spring.batch.api.products.interfaceadapters.presenters.converters.availability;

import com.spring.batch.api.products.entities.availability.ProductAvailability;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.AvailabilityDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PresenterAvailability<P extends ProductAvailability, D extends AvailabilityDto> {

    D convert(P entity);

    P convert(D dto);

    default List<D> convert(Set<P> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }

        return documents
                .stream()
                .map(this::convert).toList();
    }

    default Set<P> convert(List<D> dtos) {
        if (dtos == null) {
            return Collections.emptySet();
        }

        return dtos.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
