package com.digital.pm.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().
                info(new Info().
                        description("ProjectManagementSystem OpenApi").
                        version("v0.0.1").contact(
                                new Contact()
                                        .email("tural-98@bk.ru")
                                        .name("Guluev Tural")
                        ));
    }
}
