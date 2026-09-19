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

import com.learnify.api.dto.response.DisciplinaResponseDTO;
import com.learnify.api.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for discipline queries.
 *
 * <p>Lists all active disciplines and maps them to response DTOs containing
 * ID, type/message, active status, and creation date.
 *
 * <p>Key Features:
 * <ul>
 *     <li>List active disciplines</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    /**
     * Lists all active disciplines.
     *
     * <p>Returns a list of DTOs containing discipline ID, type (message),
     * active status, and creation date.
     *
     * @return a list of {@link DisciplinaResponseDTO} for active disciplines
     */
    public List<DisciplinaResponseDTO> listarDisciplinas() {
        return disciplinaRepository
                .findByAtivoTrue()
                .stream()
                .map(
                        disciplina -> new DisciplinaResponseDTO(
                                disciplina.getId(),
                                disciplina.getTipo().getMessage(),
                                disciplina.getAtivo(),
                                disciplina.getDataCriacao()
                        )
                )
                .toList();
    }
}