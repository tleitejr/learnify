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

import com.learnify.api.domain.entity.Progresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link Progresso} entities (user progress per content).
 *
 * <p>Provides methods to retrieve and delete progress records.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface ProgressoRepository extends JpaRepository<Progresso, UUID> {

    /**
     * Retrieves the progress record for a specific user and content item.
     *
     * @param usuarioId  the ID of the user
     * @param conteudoId the ID of the content
     * @return an {@link Optional} containing the progress record if found, or empty otherwise
     */
    Optional<Progresso> findByUsuarioIdAndConteudoId(UUID usuarioId, UUID conteudoId);

    /**
     * Deletes all progress records for a given user.
     *
     * <p>Used when deleting a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}