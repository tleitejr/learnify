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
 * Response DTO for an achievement (conquista).
 *
 * <p>Contains the details of an achievement that a user can earn
 * by reaching specific milestones (e.g., total score thresholds).
 *
 * @param id the UUID of the achievement
 * @param titulo the title of the achievement (e.g., "APRENDIZ")
 * @param descricao a descriptive text explaining the achievement criteria
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record ConquistaResponseDTO(
        UUID id,
        String titulo,
        String descricao
) {}