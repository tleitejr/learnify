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
 * Exception thrown when a requested resource is not found.
 *
 * <p>Indicates that an entity (e.g., user, content, question, alternative)
 * with the given identifier could not be located in the data store.
 *
 * <p>This exception typically results in an HTTP 404 (Not Found) response.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message indicating which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
