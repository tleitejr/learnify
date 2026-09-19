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

import com.learnify.api.domain.entity.Alternativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link Alternativa} entities.
 *
 * <p>Provides methods for querying alternatives, including by associated question.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface AlternativaRepository extends JpaRepository<Alternativa, UUID> {

    /**
     * Retrieves all alternatives for a given question.
     *
     * @param questaoId the ID of the question
     * @return a list of {@link Alternativa} entities belonging to the specified question
     */
    List<Alternativa> findByQuestaoId(UUID questaoId);
}