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
import com.learnify.api.domain.entity.ConteudoUsuario;
import com.learnify.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link ConteudoUsuario} entities (user-content associations).
 *
 * <p>Provides methods to manage content associations for users, including bulk insertion
 * and querying by user and content.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public interface ConteudoUsuarioRepository extends JpaRepository<ConteudoUsuario, UUID> {

    /**
     * Inserts missing content associations for a user.
     *
     * <p>For each content that the user does not yet have an association with,
     * creates a new {@code ConteudoUsuario} record with {@code concluido = false}.
     * This is a native SQL insert with a subquery to avoid duplicates.
     *
     * @param usuarioId the ID of the user
     * @return the number of associations inserted
     * @modifying This operation modifies the database.
     */
    @Modifying
    @Query(nativeQuery = true, value = """
    INSERT INTO conteudos_usuario (id, usuario_id, conteudo_id, concluido)
    SELECT gen_random_uuid(), :usuarioId, c.id, false
    FROM conteudos c
    WHERE NOT EXISTS (
        SELECT 1 FROM conteudos_usuario cu
        WHERE cu.usuario_id = :usuarioId
          AND cu.conteudo_id = c.id
    )
""")
    int inserirConteudosFaltantes(@Param("usuarioId") UUID usuarioId);

    /**
     * Checks whether a user has any content associations.
     *
     * @param usuarioId the ID of the user
     * @return {@code true} if at least one association exists, {@code false} otherwise
     */
    boolean existsByUsuarioId(UUID usuarioId);

    /**
     * Retrieves the association between a user and a specific content item.
     *
     * @param usuario  the user entity
     * @param conteudo the content entity
     * @return an {@link Optional} containing the association if found, or empty otherwise
     */
    Optional<ConteudoUsuario> findByUsuarioAndConteudo(Usuario usuario, Conteudo conteudo);

    /**
     * Deletes all content associations for a given user.
     *
     * <p>This is a cascading delete operation used when removing a user account.
     *
     * @param usuarioId the ID of the user
     */
    @Transactional
    void deleteByUsuarioId(UUID usuarioId);
}