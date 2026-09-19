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

import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.ConteudoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConteudoControllerTest {
    @Mock private ConteudoService conteudoService;
    @Mock private CustomUserDetails userDetails;
    @InjectMocks private ConteudoController controller;

    @Test
    void shouldListContentForAuthenticatedUserAndDiscipline() {
        
        UUID userId = UUID.randomUUID();
        UUID disciplineId = UUID.randomUUID();
        when(userDetails.getId()).thenReturn(userId);
        when(conteudoService.listarConteudos(userId, disciplineId)).thenReturn(List.of());

        
        List<?> result = controller.listar(disciplineId, userDetails);

        
        assertEquals(0, result.size());

        
        verify(conteudoService).listarConteudos(userId, disciplineId);
    }
}
