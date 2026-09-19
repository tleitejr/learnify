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

package com.learnify.api.dto.request;

import java.util.UUID;

/**
 * Request DTO for submitting a quiz answer.
 *
 * <p>Contains the identifiers for the question and the selected alternative.
 *
 * @param questaoId the UUID of the question being answered
 * @param alternativaSelecionadaId the UUID of the alternative chosen by the user
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record RespostaQuizRequestDTO(
        UUID questaoId,
        UUID alternativaSelecionadaId
) {}