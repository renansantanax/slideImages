package br.org.facc10.images.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve arquivos da pasta uploads/ como recursos estáticos
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:./uploads/"); // Pasta uploads na raiz do projeto
    }
}
