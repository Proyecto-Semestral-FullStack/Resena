package com.ms_resenas.resenas.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FrikiTienda - Reseñas API")
                        .version("1.0")
                        .description("""
                        Microservicio de gestión de reseñas de productos.
                        
                        ⚠️ Todos los endpoints requieren autenticación previa.
                        El token JWT debe enviarse a través del API Gateway (ms-gateway),
                        que lo valida con ms-auth antes de redirigir la petición.
                        """));
    }
}
