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
 * Entity representing a user access log entry.
 *
 * <p>Records login and logout timestamps, and calculates session duration
 * in seconds. Used for tracking user activity and fraud detection.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Entity
@Table(name = "registros_acesso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAcesso {

    /**
     * Unique identifier for the access log.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The user associated with this access log. Non-null.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Timestamp when the user logged in. Non-null.
     */
    @Column(name = "data_login", nullable = false)
    private LocalDateTime dataLogin;

    /**
     * Timestamp when the user logged out (can be null if session is still active).
     */
    @Column(name = "data_logout")
    private LocalDateTime dataLogout;

    /**
     * Session duration in seconds (calculated when logout occurs).
     */
    @Column(name = "duracao_segundos")
    private Long duracaoSegundos;
}
