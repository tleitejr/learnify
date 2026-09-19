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

import com.learnify.api.domain.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link Disciplina} entities.
 *
 * <p>Provides methods to retrieve active disciplines.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID> {

    /**
     * Retrieves all disciplines that are currently active.
     *
     * @return a list of active {@link Disciplina} entities
     */
    List<Disciplina> findByAtivoTrue();
}