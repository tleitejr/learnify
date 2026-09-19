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
 * Entity representing the association between a user and a content item.
 *
 * <p>This join table tracks whether a user has completed a specific content.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "conteudos_usuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoUsuario {

    /**
     * Unique identifier for the association.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The content being associated.
     */
    @ManyToOne
    @JoinColumn(name = "conteudo_id")
    private Conteudo conteudo;

    /**
     * The user associated with the content.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Indicates whether the user has completed this content.
     * Defaults to false.
     */
    @Builder.Default
    @Column(name = "concluido")
    private Boolean concluido = false;
}
