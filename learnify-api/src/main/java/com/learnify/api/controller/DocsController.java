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

package com.learnify.api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Controller responsible for serving the API documentation interface.
 *
 * <p>This controller redirects requests to the Swagger UI page, which provides
 * an interactive documentation for all REST endpoints available in the system.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Redirects to Swagger UI for API exploration</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Controller
public class DocsController {

    /**
     * Redirects to the Swagger UI documentation page.
     *
     * <p>This endpoint forwards the request to the Swagger UI entry point,
     * allowing developers and users to view and test the API endpoints
     * interactively.
     *
     * @return a forward string to the Swagger UI index page
     */
    @GetMapping("/v1/docs")
    public String documentation() {
        return "forward:/swagger-ui/index.html";
    }
}