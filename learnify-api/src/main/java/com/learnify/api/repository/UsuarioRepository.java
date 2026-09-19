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

import com.learnify.api.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link Usuario} entities.
 *
 * <p>Provides methods for user lookup by email, existence checks, and ranking retrieval.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Retrieves a user by their email address (used as username for authentication).
     *
     * @param email the user's email
     * @return an {@link Optional} containing the user if found, or empty otherwise
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Checks whether a user with the given email already exists.
     *
     * @param email the user's email
     * @return {@code true} if the email is already registered, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves a paginated list of all users ordered by total score descending.
     *
     * <p>Used for building the ranking leaderboard.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link Usuario} entities sorted by score descending
     */
    Page<Usuario> findAllByOrderByPontuacaoTotalDesc(Pageable pageable);
}