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
 * Entity representing the association between a user and an achievement.
 *
 * <p>Records which achievements a user has earned, with a unique constraint
 * per (usuario_id, conquista_id) to prevent duplicate awards.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(
        name = "usuario_conquistas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "conquista_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioConquista {

    /**
     * Unique identifier for the association.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user who earned the achievement. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * The achievement earned. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "conquista_id", nullable = false)
    private Conquista conquista;

    /**
     * Timestamp when the achievement was earned. Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "data_conquista")
    private LocalDateTime dataConquista = LocalDateTime.now();
}
