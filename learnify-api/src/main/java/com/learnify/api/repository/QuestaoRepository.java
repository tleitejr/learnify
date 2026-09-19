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

import com.learnify.api.domain.entity.Questao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link Questao} entities.
 *
 * <p>Provides methods to retrieve questions by content.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface QuestaoRepository extends JpaRepository<Questao, UUID> {

    /**
     * Retrieves all questions associated with a specific content item.
     *
     * @param conteudoId the ID of the content
     * @return a list of {@link Questao} entities belonging to the content
     */
    List<Questao> findByConteudoId(UUID conteudoId);
}