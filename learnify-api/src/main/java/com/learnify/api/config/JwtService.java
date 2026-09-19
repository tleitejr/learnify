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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service for JWT (JSON Web Token) operations.
 *
 * <p>Handles token generation, extraction of email (subject), and validation.
 * Uses HMAC-SHA256 signing with a secret key configured via application properties.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class JwtService {

    private final Key key;

    /**
     * Constructs a JwtService with the provided secret.
     *
     * <p>The secret must be at least 32 characters long; otherwise, an
     * {@link IllegalStateException} is thrown.
     *
     * @param secret the JWT signing secret from configuration
     * @throws IllegalStateException if the secret is null or shorter than 32 characters
     */
    public JwtService(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT secret inválido!");
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a JWT token for the given email.
     *
     * <p>The token is valid for 1 hour (3600 seconds) from issuance.
     *
     * @param email the user's email (subject of the token)
     * @return a signed JWT string
     */
    public String gerarToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the email (subject) from a JWT token.
     *
     * @param token the JWT token
     * @return the email address stored in the token's subject claim
     * @throws io.jsonwebtoken.JwtException if the token is invalid or malformed
     */
    public String extrairEmail(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validates a JWT token by parsing its claims.
     *
     * <p>Returns {@code true} if the token is well-formed, signed correctly,
     * and not expired. Any parsing exception results in {@code false}.
     *
     * @param token the JWT token
     * @return {@code true} if valid, {@code false} otherwise
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}