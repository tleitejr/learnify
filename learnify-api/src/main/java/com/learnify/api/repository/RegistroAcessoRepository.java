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

import com.learnify.api.domain.entity.RegistroAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link RegistroAcesso} entities (access logs).
 *
 * <p>Provides methods to retrieve the most recent access log for a user
 * and to delete all logs for a user account.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface RegistroAcessoRepository extends JpaRepository<RegistroAcesso, UUID> {

    /**
     * Retrieves the most recent access log entry for a user, ordered by login date descending.
     *
     * @param usuarioId the ID of the user
     * @return an {@link Optional} containing the latest log if found, or empty otherwise
     */
    Optional<RegistroAcesso> findTopByUsuarioIdOrderByDataLoginDesc(UUID usuarioId);

    /**
     * Deletes all access log entries for a given user.
     *
     * <p>Used when deleting a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}