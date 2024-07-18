package com.spring.batch.api.products.utils.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Value("${swagger.gateway.address}")
    private String gateway;

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Gerenciamento de Estoque")
                                .description("API para gerenciamento e atualização em batch de produtos")
                                .version("1.0.0")
                ).addServersItem(new Server().url(gateway))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")).components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                        )
                );
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Estoque")
                .packagesToScan("com.spring.batch.api.products.frameworks.web")
                .build();
    }
}
