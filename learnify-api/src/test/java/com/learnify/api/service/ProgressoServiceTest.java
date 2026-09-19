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

package com.learnify.api.service;

import com.learnify.api.domain.entity.Progresso;
import com.learnify.api.repository.ProgressoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgressoServiceTest {

    @Mock
    private ProgressoRepository progressoRepository;

    @InjectMocks
    private ProgressoService progressoService;

    @Test
    void shouldUpdateExistingProgress() {
        
        UUID usuarioId = UUID.randomUUID();
        UUID conteudoId = UUID.randomUUID();
        Progresso progresso = new Progresso();
        when(progressoRepository.findByUsuarioIdAndConteudoId(usuarioId, conteudoId)).thenReturn(Optional.of(progresso));

        
        progressoService.atualizarProgresso(usuarioId, conteudoId, 10);

        
        assertEquals(1.1, progresso.getPercentual());
        assertTrue(progresso.getConcluido());

        
        verify(progressoRepository).save(progresso);
    }

    @Test
    void shouldDoNothingWhenProgressDoesNotExist() {
        
        UUID usuarioId = UUID.randomUUID();
        UUID conteudoId = UUID.randomUUID();
        when(progressoRepository.findByUsuarioIdAndConteudoId(usuarioId, conteudoId)).thenReturn(Optional.empty());

        
        progressoService.atualizarProgresso(usuarioId, conteudoId, 0);

        

        
        verify(progressoRepository, never()).save(any());
    }
}
