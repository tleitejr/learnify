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

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.entity.UsuarioConquista;
import com.learnify.api.repository.ConquistaRepository;
import com.learnify.api.repository.UsuarioConquistaRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service responsible for gamification rules and achievement unlocking.
 *
 * <p>Checks a user's total score against achievement thresholds and unlocks
 * achievements that have not yet been earned, persisting the user-achievement
 * association.
 *
 * <p>Currently evaluates achievements such as APRENDIZ and ESPECIALISTA.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Verify score-based achievements</li>
 *     <li>Avoid duplicate achievement unlocks</li>
 *     <li>Persist unlocked achievements</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class GamificacaoService {

    private static final Logger log = LoggerFactory.getLogger(GamificacaoService.class);

    private final UsuarioConquistaRepository usuarioConquistaRepository;
    private final ConquistaRepository conquistaRepository;

    public GamificacaoService(
            UsuarioConquistaRepository usuarioConquistaRepository,
            ConquistaRepository conquistaRepository
    ) {
        this.usuarioConquistaRepository = usuarioConquistaRepository;
        this.conquistaRepository = conquistaRepository;
    }

    /**
     * Checks and unlocks achievements based on the user's total score.
     *
     * <p>Currently verifies if the user's total points meet the threshold for
     * "APRENDIZ" (10 points) or "ESPECIALISTA" (500 points). If the achievement
     * is not already earned, it is unlocked and persisted.
     *
     * @param usuario the user entity
     * @return an {@link Optional} containing the title of the newly unlocked
     *         achievement, or empty if none was earned
     * @throws RuntimeException if the achievement definition is not found in the database
     */
    public Optional<String> verificarConquistas(Usuario usuario) {
        log.info("Verificando conquistas... | ID do usuário: {}", usuario.getId());

        if (usuario.getPontuacaoTotal() >= 10) {
            Optional<String> conquista = liberarConquista(usuario, "APRENDIZ");
            if (conquista.isPresent()) return conquista;
        }

        if (usuario.getPontuacaoTotal() >= 500) {
            Optional<String> conquista = liberarConquista(usuario, "ESPECIALISTA");
            if (conquista.isPresent()) return conquista;
        }

        return Optional.empty();
    }

    private Optional<String> liberarConquista(Usuario usuario, String codigo) {
        var conquista = conquistaRepository
                .findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Conquista não encontrada!"));

        boolean jaTem = usuarioConquistaRepository
                .existsByUsuarioIdAndConquistaId(usuario.getId(), conquista.getId());

        if (jaTem) return Optional.empty();

        UsuarioConquista uc = new UsuarioConquista();
        uc.setUsuario(usuario);
        uc.setConquista(conquista);
        uc.setDataConquista(LocalDateTime.now());

        usuarioConquistaRepository.save(uc);

        log.info("Conquista desbloqueada! | ID do usuário: {} | Conquista: {}", usuario.getId(), codigo);

        return Optional.of(conquista.getTitulo());
    }
}