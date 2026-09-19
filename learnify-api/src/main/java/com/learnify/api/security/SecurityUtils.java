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

package com.learnify.api.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security-related operations.
 *
 * <p>Provides convenient static methods to access security context information,
 * such as the currently authenticated user's email.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public class SecurityUtils {

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * <p>This method extracts the principal name from the current security context.
     * It assumes the user is authenticated; otherwise, it may return {@code null}
     * or throw an exception depending on the context configuration.
     *
     * @return the email (username) of the logged-in user
     */
    public static String getEmailUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}