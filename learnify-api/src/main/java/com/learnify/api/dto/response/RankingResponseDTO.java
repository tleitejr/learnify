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
 * Response DTO for the user ranking (leaderboard).
 *
 * <p>Contains user information used in the ranking list, ordered by total score
 * descending. The {@code id} field is a generated string for the frontend,
 * not the actual user UUID (to avoid exposing internal identifiers).
 *
 * @param id            a generated string identifier (not the actual user UUID)
 * @param nomeUsuario   the user's display name
 * @param nivel         the user's current level
 * @param pontuacaoTotal the user's total accumulated points
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record RankingResponseDTO(
        String id,
        String nomeUsuario,
        Integer nivel,
        Integer pontuacaoTotal
) {}