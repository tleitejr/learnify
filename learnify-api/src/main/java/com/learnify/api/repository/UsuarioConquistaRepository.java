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

import com.learnify.api.domain.entity.UsuarioConquista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link UsuarioConquista} entities (user-achievement associations).
 *
 * <p>Provides methods to retrieve achievements earned by a user, check for existing
 * associations, and delete all associations for a user account.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface UsuarioConquistaRepository extends JpaRepository<UsuarioConquista, UUID> {

    /**
     * Retrieves all achievement associations for a given user.
     *
     * @param usuarioId the ID of the user
     * @return a list of {@link UsuarioConquista} entities
     */
    List<UsuarioConquista> findByUsuarioId(UUID usuarioId);

    /**
     * Checks whether a user has already earned a specific achievement.
     *
     * @param usuarioId   the ID of the user
     * @param conquistaId the ID of the achievement
     * @return {@code true} if the association exists, {@code false} otherwise
     */
    boolean existsByUsuarioIdAndConquistaId(UUID usuarioId, UUID conquistaId);

    /**
     * Deletes all achievement associations for a given user.
     *
     * <p>Used when deleting a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}