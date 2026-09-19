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

import com.learnify.api.dto.response.RankingResponseDTO;
import com.learnify.api.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for user ranking queries.
 *
 * <p>Provides a paginated ranking of users ordered by total score in descending
 * order, including name, level, and total points.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Paginated ranking by total score</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class RankingService {

    private final UsuarioRepository usuarioRepository;
    private static final Logger log = LoggerFactory.getLogger(RankingService.class);

    public RankingService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Retrieves a paginated ranking of users ordered by total score descending.
     *
     * <p>The ranking includes each user's name, level, and total points.
     * The result is paginated using the provided {@link Pageable} object.
     *
     * @param pageable pagination information (page, size, sort)
     * @return a page of {@link RankingResponseDTO} representing the ranking
     */
    public Page<RankingResponseDTO> obterRanking(Pageable pageable) {

        log.info("Carregando ranking... | Página: {} | Tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());

        return usuarioRepository.findAllByOrderByPontuacaoTotalDesc(pageable)
                .map(usuario -> new RankingResponseDTO(
                        UUID.randomUUID().toString(),
                        usuario.getNome(),
                        usuario.getNivel(),
                        usuario.getPontuacaoTotal()
                ));
    }
}