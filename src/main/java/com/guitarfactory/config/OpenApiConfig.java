package com.guitarfactory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI guitarFactoryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Guitar Factory System API")
                        .description("REST API for managing custom guitar orders and factory workflow")
                        .version("1.0.0"));
    }
}
