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

import java.util.UUID;

/**
 * Entity representing a quiz question.
 *
 * <p>Each question has a statement, a number (order), and belongs to a content.
 * The statement must be unique and non-null.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "questoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Questao {

    /**
     * Unique identifier for the question.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The question statement. Must be unique and non-null.
     * Stored as TEXT.
     */
    @Column(columnDefinition = "TEXT", unique = true, nullable = false)
    private String enunciado;

    /**
     * The question number (order within the content). Non-null.
     */
    @Column(name = "num_questao", nullable = false)
    private Integer numero;

    /**
     * The content to which this question belongs. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "conteudo_id", nullable = false)
    private Conteudo conteudo;
}
