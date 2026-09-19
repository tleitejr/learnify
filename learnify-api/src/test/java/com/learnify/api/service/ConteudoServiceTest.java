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

import com.learnify.api.dto.response.ConteudoResponseDTO;
import com.learnify.api.repository.ConteudoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConteudoService Tests")
class ConteudoServiceTest {

    @Mock
    private ConteudoRepository conteudoRepository;

    @InjectMocks
    private ConteudoService conteudoService;

    @Test
    @DisplayName("Should list content for the requested user and discipline")
    void testListarConteudos() {
    UUID usuarioId = UUID.randomUUID();
    UUID disciplinaId = UUID.randomUUID();
    LocalDateTime dataCriacao = LocalDateTime.now();
    ConteudoResponseDTO primeiro = new ConteudoResponseDTO(
        UUID.randomUUID(), "Frações", true, false, dataCriacao);
    ConteudoResponseDTO segundo = new ConteudoResponseDTO(
        UUID.randomUUID(), "Equações", true, true, dataCriacao);

    when(conteudoRepository.findConteudosByDisciplinaAndUsuario(disciplinaId, usuarioId))
        .thenReturn(List.of(primeiro, segundo));

    List<ConteudoResponseDTO> result = conteudoService.listarConteudos(usuarioId, disciplinaId);

    assertEquals(List.of(primeiro, segundo), result);
    verify(conteudoRepository).findConteudosByDisciplinaAndUsuario(disciplinaId, usuarioId);
    }

    @Test
    @DisplayName("Should return an empty list when no content is available")
    void testListarConteudosEmpty() {
    UUID usuarioId = UUID.randomUUID();
    UUID disciplinaId = UUID.randomUUID();
    when(conteudoRepository.findConteudosByDisciplinaAndUsuario(disciplinaId, usuarioId))
        .thenReturn(List.of());

    List<ConteudoResponseDTO> result = conteudoService.listarConteudos(usuarioId, disciplinaId);

    assertNotNull(result);
    assertEquals(List.of(), result);
    verify(conteudoRepository).findConteudosByDisciplinaAndUsuario(disciplinaId, usuarioId);
    }
}
