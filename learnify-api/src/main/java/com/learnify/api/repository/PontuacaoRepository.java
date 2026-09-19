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

package com.learnify.api.repository;

import com.learnify.api.domain.entity.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Repository interface for {@link Pontuacao} entities (score records).
 *
 * <p>Provides methods for checking existing scores and deleting user-related entries.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface PontuacaoRepository extends JpaRepository<Pontuacao, UUID> {

    /**
     * Checks whether a user has already scored points on a specific question.
     *
     * @param usuarioId the ID of the user
     * @param questaoId the ID of the question
     * @return {@code true} if a score record exists, {@code false} otherwise
     */
    boolean existsByUsuarioIdAndQuestaoId(UUID usuarioId, UUID questaoId);

    /**
     * Deletes all score records for a given user.
     *
     * <p>Used when deleting a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}