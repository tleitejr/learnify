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

package com.learnify.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a user's answer to a quiz question.
 *
 * <p>Stores the selected alternative, correctness, points earned, and timestamp.
 * Used to prevent duplicate answers and compute user statistics.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaUsuario {

    /**
     * Unique identifier for the answer record.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user who answered. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * The question that was answered. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    /**
     * The content associated with the question (denormalized for convenience).
     */
    @ManyToOne
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    /**
     * Whether the answer was correct. Non-null.
     */
    @Column(nullable = false)
    private boolean correta;

    /**
     * The alternative selected by the user. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "alternativa_id", nullable = false)
    private Alternativa alternativaSelecionada;

    /**
     * Points earned (10 if correct, 0 otherwise).
     */
    private int pontosObtidos;

    /**
     * Timestamp when the answer was submitted.
     */
    private LocalDateTime dataResposta;
}
