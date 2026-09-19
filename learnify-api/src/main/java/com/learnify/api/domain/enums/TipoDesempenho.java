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

package com.learnify.api.domain.enums;

import lombok.Getter;

/**
 * Enum representing performance classification for users based on their success rate.
 *
 * <p>Maps a percentage range to a descriptive performance level (e.g., "Excelente", "Bom").
 * Used in user statistics to provide a human-readable summary of performance.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Getter
public enum TipoDesempenho {
    /**
     * Performance level for success rate ≥ 80%.
     */
    EXCELENTE("Excelente"),

    /**
     * Performance level for success rate ≥ 75% and < 80%.
     */
    MUITO_BOM("Muito Bom"),

    /**
     * Performance level for success rate ≥ 60% and < 75%.
     */
    BOM("Bom"),

    /**
     * Performance level for success rate ≥ 50% and < 60%.
     */
    REGULAR("Regular"),

    /**
     * Performance level for success rate ≥ 30% and < 50%.
     */
    RUIM("Ruim"),

    /**
     * Performance level for success rate ≥ 1% and < 30%.
     */
    MUITO_RUIM("Muito Ruim"),

    /**
     * Performance level for users who have not answered any questions (0%).
     */
    NAO_INICIADO("Não Iniciado");

    private final String message;

    TipoDesempenho(String message) {
        this.message = message;
    }
}