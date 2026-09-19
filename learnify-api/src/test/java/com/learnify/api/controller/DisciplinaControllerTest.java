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

import com.learnify.api.service.DisciplinaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisciplinaControllerTest {
    @Mock private DisciplinaService disciplinaService;
    @InjectMocks private DisciplinaController controller;

    @Test
    void shouldListDisciplines() {
        
        when(disciplinaService.listarDisciplinas()).thenReturn(List.of());

        
        List<?> result = controller.listar();

        
        assertTrue(result.isEmpty());

        
        verify(disciplinaService).listarDisciplinas();
    }
}
