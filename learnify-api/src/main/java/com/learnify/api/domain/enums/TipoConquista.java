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

/**
 * Enum representing the types/categories of achievements (conquistas).
 *
 * <p>Classifies achievements based on the criteria used to unlock them:
 * frequency of actions, reaching a certain level, or accumulating points.
 * This allows for grouping and filtering of achievements in the gamification system.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public enum TipoConquista {
    /**
     * Achievement awarded for consistent activity (e.g., daily logins).
     */
    FREQUENCIA,

    /**
     * Achievement awarded upon reaching a specific user level.
     */
    NIVEL,

    /**
     * Achievement awarded for reaching a points milestone.
     */
    PONTOS
}