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

import com.learnify.api.domain.enums.TipoConquista;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity representing an achievement (conquista) that can be unlocked by users.
 *
 * <p>Each achievement has a code, title, description, type (e.g., PONTOS, NIVEL),
 * and a criterion value (e.g., points needed to unlock).
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "conquistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conquista {

    /**
     * Unique identifier for the achievement.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Unique code identifying the achievement.
     * Non-null.
     */
    @Column(name = "codigo", nullable = false)
    private String codigo;

    /**
     * Title of the achievement.
     * Non-null.
     */
    @Column(name = "titulo", nullable = false)
    private String titulo;

    /**
     * Detailed description of the achievement.
     * Unique and stored as TEXT.
     */
    @Column(name = "descricao", unique = true, columnDefinition = "TEXT")
    private String descricao;

    /**
     * Type/category of the achievement.
     * Non-null, stored as a string.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConquista tipo;

    /**
     * Numeric value required to unlock the achievement.
     * Non-null.
     */
    @Column(name = "valor_criterio", nullable = false)
    private Integer valorCriterio;
}