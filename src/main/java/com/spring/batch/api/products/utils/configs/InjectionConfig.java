package com.spring.batch.api.products.utils.configs;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectionConfig<T extends ProductDto> {

    @Bean
    public ProductController<T> productController() {
        return new ProductController<T>();
    }
}
