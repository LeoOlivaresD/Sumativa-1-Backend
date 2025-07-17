package com.letrasypapeles.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configura la exposición de archivos estáticos como imágenes, hojas de estilo,
 * HTML personalizado para Swagger, entre otros.
 */
@Configuration
public class WebStaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expone todos los archivos ubicados en src/main/resources/static/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
