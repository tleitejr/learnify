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

import com.learnify.api.repository.ProgressoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for updating user progress on content.
 *
 * <p>Recalculates the progress percentage and marks the content as completed
 * when a progress record already exists for the user and content. Typically
 * invoked after a quiz answer is processed.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Update progress percentage based on earned points</li>
 *     <li>Mark content as completed</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class ProgressoService {

    private final ProgressoRepository progressoRepository;

    public ProgressoService(ProgressoRepository progressoRepository) {
        this.progressoRepository = progressoRepository;
    }

    /**
     * Updates the progress percentage for a user on a specific content item.
     *
     * <p>If a progress record exists, its percentage is recalculated based on
     * the points earned (formula: (points / 100) + 1) and marked as completed.
     * This method is typically called after answering a quiz question.
     *
     * @param usuarioId  the ID of the user
     * @param conteudoId the ID of the content
     * @param pontos     the points earned in the last activity
     */
    public void atualizarProgresso(UUID usuarioId, UUID conteudoId, int pontos) {
        progressoRepository.findByUsuarioIdAndConteudoId(usuarioId, conteudoId)
                .ifPresent(progresso -> {
                    progresso.setPercentual(((double) pontos / 100) + 1);
                    progresso.setConcluido(true);
                    progressoRepository.save(progresso);
                });
    }
}