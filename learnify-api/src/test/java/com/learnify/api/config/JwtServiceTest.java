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

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private static final String SECRET = "12345678901234567890123456789012";

    @Test
    void shouldGenerateExtractAndValidateToken() {
        
        JwtService jwtService = new JwtService(SECRET);

        
        String token = jwtService.gerarToken("user@example.com");

        
        assertEquals("user@example.com", jwtService.extrairEmail(token));
        assertTrue(jwtService.validarToken(token));
    }

    @Test
    void shouldRejectInvalidSecretAndToken() {
        
        JwtService jwtService = new JwtService(SECRET);

        
        boolean valid = jwtService.validarToken("not-a-token");

        
        assertFalse(valid);
        assertThrows(IllegalStateException.class, () -> new JwtService("short"));
        assertThrows(JwtException.class, () -> jwtService.extrairEmail("not-a-token"));
    }
}