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

import java.util.UUID;

/**
 * Response DTO for user profile information.
 *
 * <p>Contains the essential user data used in responses, including
 * identification, name, email, and gamification metrics (level and score).
 *
 * @param id            the UUID of the user
 * @param nome          the user's full name
 * @param email         the user's email address
 * @param nivel         the user's current level
 * @param pontuacaoTotal the user's total accumulated score
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        Integer nivel,
        Integer pontuacaoTotal
) {}