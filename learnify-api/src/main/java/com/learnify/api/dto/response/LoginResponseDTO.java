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

package com.learnify.api.dto.response;

/**
 * Response DTO for authentication (login/signup).
 *
 * <p>Contains the JWT token and metadata required for the client
 * to authenticate subsequent API requests.
 *
 * @param token     the JWT access token
 * @param type      the token type (e.g., "Bearer")
 * @param expiresIn the token expiration time in seconds (typically 3600)
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record LoginResponseDTO(
        String token,
        String type,
        long expiresIn
) {}