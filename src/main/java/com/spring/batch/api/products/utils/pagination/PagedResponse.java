package com.spring.batch.api.products.utils.pagination;

import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse {

    private Pagination page;

    private List<ProductDto> data;
}
