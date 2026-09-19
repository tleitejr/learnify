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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for the application.
 *
 * <p>Defines security rules, authentication filters, and password encoding.
 * Public endpoints are permitted (e.g., auth endpoints, health check), while
 * all other endpoints require authentication via JWT.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Configures the security filter chain.
     *
     * <p>Disables CSRF (since JWT is used), enables CORS, and sets up
     * authorization rules:
     * <ul>
     *   <li>{@code /v1/docs} - documentation</li>
     *   <li>{@code /actuator/health} - publicly accessible (health check)</li>
     *   <li>{@code /api/v1/auth/**} - public (login, signup)</li>
     *   <li>{@code /api/v1/usuarios} - public (user registration)</li>
     *   <li>{@code /api/v1/quiz/**} - requires authentication</li>
     *   <li>All other requests - require authentication</li>
     * </ul>
     *
     * <p>Adds the {@link JwtFilter} before the default
     * {@link UsernamePasswordAuthenticationFilter}.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the built {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                    "/swagger-ui/**",
                                    "/openapi.yml"
                                ).permitAll()
                                .requestMatchers("/v1/docs").permitAll()
                                .requestMatchers("/actuator/health").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/usuarios").permitAll()
                                .requestMatchers("/api/v1/quiz/**").authenticated()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a password encoder bean using BCrypt hashing.
     *
     * <p>Used to encode user passwords before storing in the database and
     * to verify passwords during login.
     *
     * @return a {@link PasswordEncoder} instance (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}