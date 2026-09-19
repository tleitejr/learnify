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

package com.learnify.api.service;

import com.learnify.api.repository.ConteudoUsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Component responsible for managing associations between users and content.
 *
 * <p>Ensures that a user has a ConteudoUsuario association for every available
 * content item. This is typically executed after signup so progress tracking can
 * be initialized. The operation is idempotent: if associations already exist,
 * it does nothing.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Load missing content associations for a user</li>
 *     <li>Avoid duplicate association creation</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Component
@Transactional
public class ConteudoUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(ConteudoUsuarioService.class);

    private final ConteudoUsuarioRepository repository;

    public ConteudoUsuarioService(ConteudoUsuarioRepository repository) {
       this.repository = repository;
    }

    /**
     * Loads all available content associations for a user if not already present.
     *
     * <p>This method ensures that the user has an association (ConteudoUsuario)
     * with each content item. It is typically called after signup to prepare
     * the user's content progress tracking.
     *
     * <p>If associations already exist, the operation is skipped.
     *
     * @param usuarioId the ID of the user
     * @throws DataIntegrityViolationException if a database constraint is violated
     *         (handled internally by logging a warning)
     */
    public void loadConteudosUsuario(UUID usuarioId) {
        if (repository.existsByUsuarioId(usuarioId)) {
            log.info("Usuário {} já possui conteúdos carregados.", usuarioId);
            return;
        }
        try {
            int inseridos = repository.inserirConteudosFaltantes(usuarioId);
            log.info("{} associações criadas para o usuário {}", inseridos, usuarioId);
        } catch (DataIntegrityViolationException e) {
            log.warn("Associações já existiam para o usuário {}", usuarioId);
        }
    }
}