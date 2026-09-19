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

package com.learnify.api.exception;

import java.time.LocalDateTime;

/**
 * Standard API error response record.
 *
 * <p>Encapsulates error details returned to the client when an exception occurs.
 * Includes timestamp, HTTP status code, error category, a human-readable message,
 * and the request path that caused the error.
 *
 * @param timestamp the date and time when the error occurred
 * @param status    the HTTP status code (e.g., 404, 400, 500)
 * @param error     a short error type description (e.g., "Resource Not Found")
 * @param message   a detailed error message
 * @param path      the request URI that triggered the error
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
