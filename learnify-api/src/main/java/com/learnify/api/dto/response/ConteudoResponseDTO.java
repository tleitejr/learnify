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
 * Response DTO for a content item (conteúdo).
 *
 * <p>Contains the details of a learning content, including its completion status
 * for a specific user. Used when listing contents within a discipline.
 *
 * @param id           the UUID of the content
 * @param titulo       the title of the content
 * @param ativo        whether the content is active
 * @param concluido    whether the authenticated user has completed this content
 * @param dataCriacao  the creation date of the content
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record ConteudoResponseDTO(
        UUID id,
        String titulo,
        Boolean ativo,
        Boolean concluido,
        LocalDateTime dataCriacao
) {}