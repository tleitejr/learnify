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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocsControllerTest {
    @Test
    void shouldForwardToSwaggerUi() {
        
        DocsController controller = new DocsController();

        
        String result = controller.documentation();

        
        assertEquals("forward:/swagger-ui/index.html", result);

        
    }
}
