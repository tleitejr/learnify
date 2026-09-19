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

package com.learnify.api.domain.enums;

/**
 * Enum representing the user roles within the application.
 *
 * <p>Defines the authorization levels for users, determining which
 * actions and resources they can access. Currently supports two roles:
 * admin and student.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public enum PapelUsuario {
    /**
     * Administrator role with full access to system management features.
     */
    ADMIN,

    /**
     * Student role with access to learning content, quizzes, and personal statistics.
     */
    ESTUDANTE
}