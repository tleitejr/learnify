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

import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.dto.response.ConteudoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link Conteudo} entities.
 *
 * <p>Provides custom queries to retrieve content items with user-specific completion status.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface ConteudoRepository extends JpaRepository<Conteudo, UUID> {

    /**
     * Retrieves all content items for a given discipline, including completion status for a specific user.
     *
     * <p>Performs a left join with {@code ConteudoUsuario} to determine if the user has
     * completed each content item. If no association exists, completion defaults to {@code false}.
     *
     * @param disciplinaId the ID of the discipline
     * @param usuarioId    the ID of the user
     * @return a list of {@link ConteudoResponseDTO} containing content details and completion flag
     */
    @Query("""
        SELECT DISTINCT new com.learnify.api.dto.response.ConteudoResponseDTO(
             c.id,
             c.titulo,
             c.ativo,
             COALESCE(cu.concluido, FALSE),
             c.dataCriacao
        )
        FROM Conteudo c
        LEFT JOIN ConteudoUsuario cu
            ON cu.conteudo = c AND cu.usuario.id = :usuarioId
        WHERE c.disciplina.id = :disciplinaId
    """)
    List<ConteudoResponseDTO> findConteudosByDisciplinaAndUsuario(
            @Param("disciplinaId") UUID disciplinaId,
            @Param("usuarioId") UUID usuarioId
    );
}