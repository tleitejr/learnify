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

import com.learnify.api.domain.entity.RespostaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link RespostaUsuario} entities (user answers).
 *
 * <p>Provides comprehensive querying capabilities for user responses, including
 * counts, correctness checks, duplicate prevention, and aggregation of points.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface RespostaUsuarioRepository extends JpaRepository<RespostaUsuario, UUID> {

    /**
     * Counts the total number of answers given by a user.
     *
     * @param usuarioId the ID of the user
     * @return the total answer count
     */
    long countByUsuarioId(UUID usuarioId);

    /**
     * Counts the number of correct answers given by a user.
     *
     * @param usuarioId the ID of the user
     * @return the count of correct answers
     */
    long countByUsuarioIdAndCorretaTrue(UUID usuarioId);

    /**
     * Counts the number of incorrect answers given by a user.
     *
     * @param usuarioId the ID of the user
     * @return the count of incorrect answers
     */
    long countByUsuarioIdAndCorretaFalse(UUID usuarioId);

    /**
     * Checks whether a user has already answered a specific question.
     *
     * <p>Used to prevent duplicate answers.
     *
     * @param usuarioId the ID of the user
     * @param questaoId the ID of the question
     * @return {@code true} if an answer exists, {@code false} otherwise
     */
    boolean existsByUsuarioIdAndQuestaoId(UUID usuarioId, UUID questaoId);

    /**
     * Retrieves all answers given by a user for a specific content item.
     *
     * @param usuarioId  the ID of the user
     * @param conteudoId the ID of the content
     * @return a list of {@link RespostaUsuario} entities for the content
     */
    List<RespostaUsuario> findByUsuarioIdAndConteudoId(UUID usuarioId, UUID conteudoId);

    /**
     * Calculates the average points obtained per answer for a user.
     *
     * <p>Returns 0 if no answers exist.
     *
     * @param usuarioId the ID of the user
     * @return the average points as a {@link Double}
     */
    @Query("SELECT COALESCE(AVG(r.pontosObtidos), 0) FROM RespostaUsuario r WHERE r.usuario.id = :usuarioId")
    Double mediaPontos(UUID usuarioId);

    /**
     * Deletes all answers given by a user.
     *
     * <p>Used when deleting a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}