package com.losagiles.CineAgile;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Profile("dev")
    @Bean
    public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // Apply to all endpoints
                            .allowedOrigins("*")
                            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Allowed HTTP methods
                            .allowedHeaders("*"); // Allowed headers
                }
            };
    }
}
