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

package com.learnify.api.dto.response;

import java.time.LocalDateTime;

/**
 * Response DTO for logout operation.
 *
 * <p>Confirms a successful logout with a message and timestamp.
 *
 * @param message     a confirmation message (e.g., "Logout feito com sucesso!")
 * @param dataLogout  the timestamp when the logout was recorded
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record LogoutResponseDTO(
        String message,
        LocalDateTime dataLogout
) {
}