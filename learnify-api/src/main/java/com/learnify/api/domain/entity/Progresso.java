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
 * Entity representing the progress of a user on a specific content item.
 *
 * <p>Includes completion status and percentage, with a unique constraint
 * per (usuario_id, conteudo_id) to ensure one record per user-content pair.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(
        name = "progresso",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "conteudo_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progresso {

    /**
     * Unique identifier for the progress record.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user whose progress is being tracked. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * The content being progressed. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "conteudo_id", nullable = false)
    private Conteudo conteudo;

    /**
     * Whether the content has been fully completed. Defaults to false.
     */
    @Builder.Default
    private Boolean concluido = false;

    /**
     * Progress percentage (0.0 to 1.0). Defaults to 0.0.
     */
    @Builder.Default
    private Double percentual = 0.0;

    /**
     * Timestamp of the last update. Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao = LocalDateTime.now();
}
