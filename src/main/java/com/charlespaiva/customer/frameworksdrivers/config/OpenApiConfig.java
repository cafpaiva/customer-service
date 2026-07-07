package com.charlespaiva.customer.frameworksdrivers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Frameworks & Drivers (círculo 4) — metadados do OpenAPI/Swagger. */
@Configuration
class OpenApiConfig {

    @Bean
    OpenAPI customerServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Customer Service API")
                .description("Cadastro de Clientes — Clean Architecture")
                .version("1.0.0"));
    }
}
