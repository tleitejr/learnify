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
 * Entity representing an alternative option for a quiz question.
 *
 * <p>Each question has multiple alternatives, one of which is marked as correct.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "alternativas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alternativa {

    /**
     * Unique identifier for the alternative.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The descriptive text of the alternative.
     * Non-null.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    /**
     * Indicates whether this alternative is the correct answer.
     * Non-null.
     */
    @Column(name = "correta", nullable = false)
    private Boolean correta;

    /**
     * The question to which this alternative belongs.
     * Non-null relationship.
     */
    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;
}
