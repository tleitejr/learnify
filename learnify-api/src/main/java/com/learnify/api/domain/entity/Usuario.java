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

import com.learnify.api.domain.enums.PapelUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a user account.
 *
 * <p>Contains authentication credentials, profile information, and gamification
 * metrics (level and total score). Users can have roles (ADMIN or ESTUDANTE).
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user's full name. Non-null, max length 150.
     */
    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * The user's email address. Unique, non-null, max length 150.
     */
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /**
     * The user's encrypted password. Non-null.
     */
    @Column(nullable = false)
    private String senha;

    /**
     * The user's role (ADMIN or ESTUDANTE). Non-null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelUsuario papel;

    /**
     * The user's level (based on total points). Defaults to 1.
     */
    @Builder.Default
    private Integer nivel = 1;

    /**
     * The user's total accumulated points. Defaults to 0.
     */
    @Builder.Default
    @Column(name = "pontuacao_total")
    private Integer pontuacaoTotal = 0;

    /**
     * Indicates whether the user account is active. Defaults to true.
     */
    @Builder.Default
    private Boolean ativo = true;

    /**
     * Timestamp when the account was created. Defaults to the current time.
     */
    @Builder.Default
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
