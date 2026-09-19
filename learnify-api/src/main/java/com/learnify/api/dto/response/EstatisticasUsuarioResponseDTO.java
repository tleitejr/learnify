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

import java.math.BigDecimal;

/**
 * Response DTO for user statistics.
 *
 * <p>Contains comprehensive performance metrics for a user, including
 * answer counts, success percentage, total score, level, and a
 * descriptive performance classification (e.g., EXCELENTE, BOM).
 *
 * @param totalRespostas total number of questions answered
 * @param acertos        number of correct answers
 * @param erros          number of incorrect answers
 * @param percentual     success percentage (correct answers / total answers)
 * @param pontuacaoTotal user's total accumulated score
 * @param mediaPontos    average points per answered question
 * @param nivel          user's current level (based on total score)
 * @param desempenho     performance classification message
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record EstatisticasUsuarioResponseDTO(
        long totalRespostas,
        long acertos,
        long erros,
        BigDecimal percentual,
        int pontuacaoTotal,
        BigDecimal mediaPontos,
        int nivel,
        String desempenho
) {}