package com.spring.batch.api.products.interfaceadapters.presenters.converters;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.Dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Presenter<T, D extends Dto> {

    D convert(T entity);

    T convert(D dto);

    default List<D> convertListEntity(List<T> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }

        return documents.stream().map(this::convert).toList();
    }

    default List<T> convertListDtos(List<D> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }

        List<T> documents = new ArrayList<>();

        dtos.forEach(item -> documents.add(convert(item)));

        return documents;
    }
}
