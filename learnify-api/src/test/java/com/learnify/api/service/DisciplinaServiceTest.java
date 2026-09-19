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

import com.learnify.api.domain.entity.Disciplina;
import com.learnify.api.domain.enums.TipoDisciplina;
import com.learnify.api.dto.response.DisciplinaResponseDTO;
import com.learnify.api.repository.DisciplinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DisciplinaService Tests")
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @SuppressWarnings("unused")
    private Disciplina testDisciplina;

    @BeforeEach
    void setUp() {
        testDisciplina = Disciplina.builder()
                .id(UUID.randomUUID())
                .tipo(TipoDisciplina.MATEMATICA)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should list all active disciplines")
    void testListarDisciplinas() {
        
        Disciplina matematica = Disciplina.builder()
                .id(UUID.randomUUID())
                .tipo(TipoDisciplina.MATEMATICA)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        Disciplina portugues = Disciplina.builder()
                .id(UUID.randomUUID())
                .tipo(TipoDisciplina.PORTUGUES)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        when(disciplinaRepository.findByAtivoTrue())
                .thenReturn(List.of(matematica, portugues));

        
        List<DisciplinaResponseDTO> result = disciplinaService.listarDisciplinas();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TipoDisciplina.MATEMATICA.getMessage(), result.get(0).titulo());
        assertEquals(TipoDisciplina.PORTUGUES.getMessage(), result.get(1).titulo());
        assertTrue(result.get(0).ativo());
        verify(disciplinaRepository).findByAtivoTrue();
    }

    @Test
    @DisplayName("Should return empty list when no active disciplines exist")
    void testListarDisciplinasEmpty() {
        
        when(disciplinaRepository.findByAtivoTrue())
                .thenReturn(List.of());

        
        List<DisciplinaResponseDTO> result = disciplinaService.listarDisciplinas();

        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should map discipline entity to DTO correctly")
    void testDisciplinaMapping() {
        
        LocalDateTime now = LocalDateTime.now();
        Disciplina disciplina = Disciplina.builder()
                .id(UUID.randomUUID())
                .tipo(TipoDisciplina.HISTORIA)
                .ativo(true)
                .dataCriacao(now)
                .build();

        when(disciplinaRepository.findByAtivoTrue())
                .thenReturn(List.of(disciplina));

        
        List<DisciplinaResponseDTO> result = disciplinaService.listarDisciplinas();

        
        assertEquals(1, result.size());
        assertEquals(disciplina.getId(), result.get(0).id());
        assertEquals(TipoDisciplina.HISTORIA.getMessage(), result.get(0).titulo());
        assertEquals(true, result.get(0).ativo());
    }
}
