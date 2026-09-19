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

package com.learnify.api.controller;

import com.learnify.api.dto.response.ConteudoResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.ConteudoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for accessing learning content within a discipline.
 *
 * <p>This controller handles requests that require a specific discipline context
 * and returns the available educational content (modules, lessons, or topics)
 * for that discipline, respecting the user's progress and permissions.
 *
 * <p>Key Features:
 * <ul>
 *     <li>List all contents belonging to a discipline</li>
 *     <li>Filter content based on user's unlocked status</li>
 *     <li>Support content ordering and basic metadata</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/disciplinas")
public class ConteudoController {

    private final ConteudoService conteudoService;

    public ConteudoController(ConteudoService conteudoService) {
        this.conteudoService = conteudoService;
    }

    /**
     * Lists all contents (lessons, modules, etc.) for a given discipline,
     * adapted to the authenticated user's state.
     *
     * <p>The method checks which contents the user has already started or completed
     * and returns only those that are currently accessible.
     *
     * @param disciplinaId the unique identifier of the discipline
     * @param userDetails the authenticated user details
     * @return a list of content DTOs, each containing id, title, description, and progress status
     */
    @GetMapping("/{disciplinaId}/conteudos")
    public List<ConteudoResponseDTO> listar(
            @PathVariable UUID disciplinaId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return conteudoService.listarConteudos(userDetails.getId(), disciplinaId);
    }
}