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

import com.learnify.api.domain.enums.TipoDisciplina;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a subject/discipline.
 *
 * <p>Each discipline has a type (e.g., "Matemática"), an active status,
 * and a creation timestamp.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disciplinas")
public class Disciplina {

    /**
     * Unique identifier for the discipline.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The type of discipline (e.g., PORTUGUES, MATEMATICA).
     * Unique, non-null, stored as a string.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", unique = true, nullable = false)
    private TipoDisciplina tipo;

    /**
     * Indicates whether the discipline is active.
     * Defaults to true.
     */
    @Builder.Default
    @Column(name = "ativo")
    private Boolean ativo = true;

    /**
     * Timestamp when the discipline was created.
     * Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
