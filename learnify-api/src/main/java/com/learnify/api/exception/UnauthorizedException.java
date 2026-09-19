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

/**
 * Exception thrown when authentication fails or is missing.
 *
 * <p>Indicates that the request lacks valid authentication credentials
 * (e.g., invalid password, expired JWT, or missing token) or that the
 * authenticated user does not have permission to access the resource.
 *
 * <p>This exception typically results in an HTTP 401 (Unauthorized) response.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message the detail message explaining the authentication failure
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
