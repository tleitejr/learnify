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
 * Response DTO for a question alternative.
 *
 * <p>Contains the basic information of an alternative option for a quiz question,
 * including its unique identifier and descriptive text.
 *
 * @param id        the UUID of the alternative
 * @param descricao the descriptive text of the alternative
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record AlternativaResponseDTO(
        UUID id,
        String descricao
) {}