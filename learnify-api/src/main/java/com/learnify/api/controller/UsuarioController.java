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

package com.learnify.api.controller;

import com.learnify.api.dto.response.ConquistaResponseDTO;
import com.learnify.api.dto.response.EstatisticasUsuarioResponseDTO;
import com.learnify.api.dto.response.UsuarioResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.ConteudoUsuarioService;
import com.learnify.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing authenticated user data.
 *
 * <p>This controller provides endpoints for logged-in users to retrieve their
 * profile information, achievements, and statistics. All endpoints require
 * authentication and are based on the user extracted from the security context.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Get current user profile</li>
 *     <li>List user's unlocked achievements</li>
 *     <li>Obtain user performance statistics</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ConteudoUsuarioService conteudoUsuarioService;

    public UsuarioController(
            UsuarioService usuarioService,
            ConteudoUsuarioService conteudoUsuarioService
    ) {
        this.usuarioService = usuarioService;
        this.conteudoUsuarioService = conteudoUsuarioService;
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * <p>Before returning the user data, it loads the user's associated content
     * (e.g., started or completed modules) to keep session state up to date.
     *
     * @param user the authenticated user details extracted from the security context
     * @return a DTO containing the user's id, name, email, level, and total points
     */
    @GetMapping("/me")
    public UsuarioResponseDTO me(@AuthenticationPrincipal CustomUserDetails user) {
        conteudoUsuarioService.loadConteudosUsuario(user.getId());
        return new UsuarioResponseDTO(
                user.getUsuario().getId(),
                user.getUsuario().getNome(),
                user.getUsuario().getEmail(),
                user.getUsuario().getNivel(),
                user.getUsuario().getPontuacaoTotal()
        );
    }

    /**
     * Lists all achievements earned by the authenticated user.
     *
     * @param user the authenticated user details
     * @return a list of achievement DTOs with metadata (name, description, icon, etc.)
     */
    @GetMapping("/me/conquistas")
    public ResponseEntity<List<ConquistaResponseDTO>> minhasConquistas(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(usuarioService.listarConquistas(user.getId()));
    }

    /**
     * Provides learning statistics for the authenticated user.
     *
     * <p>Typical statistics include total completed contents, average quiz scores,
     * current level progression, and points earned.
     *
     * @param user the authenticated user entity
     * @return a DTO containing various statistical indicators
     */
    @GetMapping("/me/estatisticas")
    public ResponseEntity<EstatisticasUsuarioResponseDTO> estatisticas(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(usuarioService.obterEstatisticas(user.getUsuario()));
    }
}