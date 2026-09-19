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
 * Response DTO for a quiz answer result.
 *
 * <p>Returns the outcome of a user's answer to a quiz question, including
 * correctness, points earned, updated score and level, and any newly
 * unlocked achievement.
 *
 * @param correta              whether the selected answer was correct
 * @param pontosGanhos         points earned from this answer (10 if correct, 0 otherwise)
 * @param pontuacaoTotal       updated total score after this answer
 * @param nivel                updated level after this answer
 * @param conquistaDesbloqueada title of the newly unlocked achievement, or null if none
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record ResultadoQuizResponseDTO(
        boolean correta,
        Integer pontosGanhos,
        Integer pontuacaoTotal,
        Integer nivel,
        String conquistaDesbloqueada
) {}