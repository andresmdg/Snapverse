package org.codecollad.snapverse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true).maxAge(3600);

        registry.addMapping("/auth/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                .allowCredentials(false).maxAge(3600);
    }

}
