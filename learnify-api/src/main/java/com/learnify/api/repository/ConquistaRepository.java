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

import com.learnify.api.domain.entity.Conquista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link Conquista} entities.
 *
 * <p>Provides methods for retrieving achievements by their unique code.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface ConquistaRepository extends JpaRepository<Conquista, UUID> {

    /**
     * Finds an achievement by its unique code (e.g., "APRENDIZ").
     *
     * @param codigo the achievement code
     * @return an {@link Optional} containing the achievement if found, or empty otherwise
     */
    Optional<Conquista> findByCodigo(String codigo);
}