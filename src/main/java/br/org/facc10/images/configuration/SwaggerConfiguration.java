package br.org.facc10.images.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {
    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI().components(new Components())
                .info(new Info().title("Image Upload")
                        .description("API para upload de imagem").version("v1"));
    }
}