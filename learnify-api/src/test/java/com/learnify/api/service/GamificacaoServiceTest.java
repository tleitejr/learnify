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

import com.learnify.api.domain.entity.Conquista;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.repository.ConquistaRepository;
import com.learnify.api.repository.UsuarioConquistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GamificacaoServiceTest {

    @Mock
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Mock
    private ConquistaRepository conquistaRepository;

    @InjectMocks
    private GamificacaoService gamificacaoService;

    @Test
    void shouldUnlockApprenticeAchievementAtTenPoints() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).pontuacaoTotal(10).build();
        Conquista conquista = Conquista.builder().id(UUID.randomUUID()).titulo("Aprendiz").build();
        when(conquistaRepository.findByCodigo("APRENDIZ")).thenReturn(Optional.of(conquista));
        when(usuarioConquistaRepository.existsByUsuarioIdAndConquistaId(usuario.getId(), conquista.getId())).thenReturn(false);

        
        Optional<String> result = gamificacaoService.verificarConquistas(usuario);

        
        assertEquals(Optional.of("Aprendiz"), result);

        
        verify(usuarioConquistaRepository).save(any());
        verify(conquistaRepository, never()).findByCodigo("ESPECIALISTA");
    }

    @Test
    void shouldReturnEmptyWhenAchievementAlreadyExists() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).pontuacaoTotal(10).build();
        Conquista conquista = Conquista.builder().id(UUID.randomUUID()).build();
        when(conquistaRepository.findByCodigo("APRENDIZ")).thenReturn(Optional.of(conquista));
        when(usuarioConquistaRepository.existsByUsuarioIdAndConquistaId(usuario.getId(), conquista.getId())).thenReturn(true);

        
        Optional<String> result = gamificacaoService.verificarConquistas(usuario);

        
        assertFalse(result.isPresent());

        
        verify(usuarioConquistaRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenAchievementDefinitionDoesNotExist() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).pontuacaoTotal(10).build();
        when(conquistaRepository.findByCodigo("APRENDIZ")).thenReturn(Optional.empty());

        
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> gamificacaoService.verificarConquistas(usuario));

        
        assertEquals("Conquista não encontrada!", exception.getMessage());

        
        verify(usuarioConquistaRepository, never()).save(any());
    }

    @Test
    void shouldCheckSpecialistAchievementAfterApprenticeAlreadyExists() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).pontuacaoTotal(500).build();
        Conquista apprentice = Conquista.builder().id(UUID.randomUUID()).titulo("Aprendiz").build();
        Conquista specialist = Conquista.builder().id(UUID.randomUUID()).titulo("Especialista").build();
        when(conquistaRepository.findByCodigo("APRENDIZ")).thenReturn(Optional.of(apprentice));
        when(conquistaRepository.findByCodigo("ESPECIALISTA")).thenReturn(Optional.of(specialist));
        when(usuarioConquistaRepository.existsByUsuarioIdAndConquistaId(usuario.getId(), apprentice.getId())).thenReturn(true);
        when(usuarioConquistaRepository.existsByUsuarioIdAndConquistaId(usuario.getId(), specialist.getId())).thenReturn(false);

        
        Optional<String> result = gamificacaoService.verificarConquistas(usuario);

        
        assertEquals(Optional.of("Especialista"), result);

        
        verify(usuarioConquistaRepository).save(any());
    }
}
