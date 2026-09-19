/*
 * Copyright (c) 2026 Antonio C. Leite Jr
 *
 * Learnify
 *
 * Final Year Project (Academic Capstone)
 * Bachelor in Software Engineering
 *
 * All rights reserved.
 */

package com.learnify.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) configuration for the application.
 *
 * <p>Defines allowed origins, HTTP methods, headers, and credentials for
 * cross-origin requests. This enables the frontend (e.g., a React/Vue app
 * running on localhost:5173) to communicate with the API.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Configuration
public class CorsConfig {

    /**
     * Creates a {@link WebMvcConfigurer} bean with custom CORS mappings.
     *
     * <p>Allows all endpoints (/**) to accept requests from the specified origins,
     * with common HTTP methods, any headers, and support for credentials.
     * Preflight requests (OPTIONS) are cached for 3600 seconds.
     *
     * @return a configured {@link WebMvcConfigurer} instance
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "http://127.0.0.1:5173"
                        )
                        .allowedMethods(
                                "POST",
                                "GET",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}