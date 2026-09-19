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
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a score record for a user's correct answer.
 *
 * <p>Each record links a user, a content, and a question, with points earned
 * and the timestamp of completion.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "pontuacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pontuacao {

    /**
     * Unique identifier for the score record.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user who earned the points. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * The content associated with the question. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "conteudo_id", nullable = false)
    private Conteudo conteudo;

    /**
     * The question that was answered correctly. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    /**
     * The number of points earned (typically 10 for a correct answer).
     * Non-null.
     */
    @Column(name = "pontos_obtidos", nullable = false)
    private Integer pontosObtidos;

    /**
     * Timestamp when the points were earned.
     * Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao = LocalDateTime.now();
}
