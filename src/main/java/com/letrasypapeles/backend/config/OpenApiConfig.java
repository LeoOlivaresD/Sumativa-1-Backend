package com.letrasypapeles.backend.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Letras y Papeles")
                        .version("1.0.1")
                        .description("""
                                Backend de la aplicación Letras y Papeles.
                                
                                Autores:
                                - Leonardo Olivares (leo.olivares@ejemplo.com) - [GitHub](https://github.com/LeoOlivaresD)
                                - Jaime Barraza (jai.barraza@duocuc.cl) - [GitHub](https://github.com/loco-linux)
                                """)
                        .contact(new Contact()
                                .name("Leonardo Olivares y Jaime Barraza")
                                .email("leo.olivares@duocuc.cl")
                                .url("https://github.com/LeoOlivaresD")
                        )
                );
    }
}
