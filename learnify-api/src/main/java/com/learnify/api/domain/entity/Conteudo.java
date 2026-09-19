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
 * Entity representing a learning content item.
 *
 * <p>Each content belongs to a discipline and can contain multiple questions.
 * Contents can be activated/deactivated and have a creation timestamp.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "conteudos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conteudo {

    /**
     * Unique identifier for the content.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Title of the content. Must be unique and non-null.
     */
    @Column(name = "titulo", unique = true, nullable = false)
    private String titulo;

    /**
     * The discipline to which this content belongs.
     * Non-null relationship.
     */
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    /**
     * Indicates whether the content is active (available for users).
     * Defaults to true.
     */
    @Builder.Default
    @Column(name = "ativo")
    private Boolean ativo = true;

    /**
     * Timestamp when the content was created.
     * Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
