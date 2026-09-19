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

import com.learnify.api.repository.ConteudoUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConteudoUsuarioServiceTest {

    @Mock
    private ConteudoUsuarioRepository repository;

    @InjectMocks
    private ConteudoUsuarioService conteudoUsuarioService;

    @Test
    void shouldSkipLoadingWhenUserAlreadyHasContent() {
        
        UUID usuarioId = UUID.randomUUID();
        when(repository.existsByUsuarioId(usuarioId)).thenReturn(true);

        
        conteudoUsuarioService.loadConteudosUsuario(usuarioId);

        

        
        verify(repository, never()).inserirConteudosFaltantes(usuarioId);
    }

    @Test
    void shouldInsertMissingContentAssociations() {
        
        UUID usuarioId = UUID.randomUUID();
        when(repository.existsByUsuarioId(usuarioId)).thenReturn(false);
        when(repository.inserirConteudosFaltantes(usuarioId)).thenReturn(3);

        
        conteudoUsuarioService.loadConteudosUsuario(usuarioId);

        

        
        verify(repository).inserirConteudosFaltantes(usuarioId);
    }

    @Test
    void shouldHandleConcurrentAssociationCreation() {
        
        UUID usuarioId = UUID.randomUUID();
        when(repository.existsByUsuarioId(usuarioId)).thenReturn(false);
        when(repository.inserirConteudosFaltantes(usuarioId)).thenThrow(new DataIntegrityViolationException("duplicate"));

        
        conteudoUsuarioService.loadConteudosUsuario(usuarioId);

        

        
        verify(repository).inserirConteudosFaltantes(usuarioId);
    }
}
