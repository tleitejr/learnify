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

import lombok.Getter;

/**
 * Enum representing the available subjects/disciplines in the learning platform.
 *
 * <p>Lists all academic disciplines offered, each with a user-friendly display
 * message in Portuguese. Used to categorize content and quizzes.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Getter
public enum TipoDisciplina {
    /**
     * Portuguese language.
     */
    PORTUGUES("Português"),

    /**
     * Mathematics.
     */
    MATEMATICA("Matemática"),

    /**
     * English language.
     */
    INGLES("Inglês"),

    /**
     * Arts.
     */
    ARTES("Artes"),

    /**
     * Physical Education.
     */
    EDUCACAO_FISICA("Educação Física"),

    /**
     * History.
     */
    HISTORIA("História"),

    /**
     * Geography.
     */
    GEOGRAFIA("Geografia"),

    /**
     * Philosophy.
     */
    FILOSOFIA("Filosofia"),

    /**
     * Sociology.
     */
    SOCIOLOGIA("Sociologia"),

    /**
     * Physics.
     */
    FISICA("Física"),

    /**
     * Chemistry.
     */
    QUIMICA("Química"),

    /**
     * Biology.
     */
    BIOLOGIA("Biologia");

    private final String message;

    TipoDisciplina(String message) {
        this.message = message;
    }
}