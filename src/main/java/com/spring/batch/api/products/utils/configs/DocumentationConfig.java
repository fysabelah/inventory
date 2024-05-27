package com.spring.batch.api.products.utils.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Gerenciamento de Estoque")
                                .description("API para gerenciamento de produtos e atualização em batch")
                                .version("1.0.0")
                );
    }
}
