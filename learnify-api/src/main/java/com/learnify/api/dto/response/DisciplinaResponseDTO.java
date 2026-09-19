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

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for a discipline (disciplina).
 *
 * <p>Contains the basic information of a subject area, including its type
 * (represented as a message string), active status, and creation date.
 *
 * @param id           the UUID of the discipline
 * @param titulo       the title or type of the discipline (message from enum)
 * @param ativo        whether the discipline is active
 * @param dataCriacao  the creation date of the discipline
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record DisciplinaResponseDTO(
        UUID id,
        String titulo,
        Boolean ativo,
        LocalDateTime dataCriacao
) {}