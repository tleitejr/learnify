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

import java.util.List;
import java.util.UUID;

/**
 * Response DTO for a quiz question.
 *
 * <p>Contains the question's statement, number, and a list of alternatives
 * for the user to choose from. Used when loading a quiz for a content.
 *
 * @param id           the UUID of the question
 * @param enunciado    the question statement/description
 * @param numero       the question number (order within the content)
 * @param alternativas the list of {@link AlternativaResponseDTO} options
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record QuestaoResponseDTO(
        UUID id,
        String enunciado,
        Integer numero,
        List<AlternativaResponseDTO> alternativas
) {}