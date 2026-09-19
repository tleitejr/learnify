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

package com.learnify.api.dto.request;

import com.learnify.api.domain.enums.PapelUsuario;

/**
 * Request DTO for user registration (signup).
 *
 * <p>Contains all necessary information to create a new user account.
 * The {@link PapelUsuario} field is optional; if not provided, the
 * service defaults to {@code ESTUDANTE} (student role).
 *
 * @param nome the user's full name
 * @param email the user's email address (must be unique)
 * @param senha the user's plain-text password (will be encoded before storage)
 * @param papelUsuario the user's role (optional, defaults to ESTUDANTE)
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        PapelUsuario papelUsuario
) {}