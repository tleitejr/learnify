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

package com.learnify.api.service;

import com.learnify.api.dto.response.ConteudoResponseDTO;
import com.learnify.api.repository.ConteudoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service responsible for retrieving learning content.
 *
 * <p>Lists content items available to a given user within a discipline, using
 * user-content associations to filter what the user can access.
 *
 * <p>Key Features:
 * <ul>
 *     <li>List content by discipline and user</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class ConteudoService {

    private final ConteudoRepository conteudoRepository;

    public ConteudoService(ConteudoRepository conteudoRepository) {
        this.conteudoRepository = conteudoRepository;
    }

    /**
     * Retrieves all content items for a given discipline, filtered by user.
     *
     * <p>Returns a list of content DTOs that belong to the specified discipline
     * and are accessible to the given user (based on user-content associations).
     *
     * @param usuarioId    the ID of the authenticated user
     * @param disciplinaId the ID of the discipline
     * @return a list of {@link ConteudoResponseDTO} representing the content items
     */
    public List<ConteudoResponseDTO> listarConteudos(UUID usuarioId, UUID disciplinaId) {
        return conteudoRepository.findConteudosByDisciplinaAndUsuario(disciplinaId, usuarioId);
    }
}