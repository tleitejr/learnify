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
 * Exception thrown when a business rule is violated.
 *
 * <p>Represents errors that occur due to invalid business logic or state,
 * such as duplicate email registration, invalid operation sequences, or
 * data that does not meet application constraints.
 *
 * <p>This exception typically results in an HTTP 400 (Bad Request) response.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public class BusinessException extends RuntimeException {
    /**
     * Constructs a new BusinessException with the specified detail message.
     *
     * @param message the detail message explaining the business rule violation
     */
    public BusinessException(String message) {
        super(message);
    }
}
