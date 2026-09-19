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

import com.learnify.api.dto.response.DisciplinaResponseDTO;
import com.learnify.api.service.DisciplinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing and listing academic disciplines.
 *
 * <p>This controller provides public endpoints to retrieve all available
 * disciplines in the platform. Disciplines represent broad subject areas
 * (e.g., Mathematics, Programming) that contain learning content.
 *
 * <p>Key Features:
 * <ul>
 *     <li>List all disciplines with basic metadata (name, description, icon)</li>
 *     <li>Order disciplines by name or relevance</li>
 *     <li>No authentication required for listing (public catalog)</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("api/v1/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    /**
     * Returns the complete list of disciplines available on the platform.
     *
     * <p>This endpoint is typically used to populate the main dashboard
     * or discipline selection screen.</p>
     *
     * @return a list of discipline DTOs containing id, name, description, and thumbnail URL
     */
    @GetMapping
    public List<DisciplinaResponseDTO> listar() {
        return disciplinaService.listarDisciplinas();
    }
}