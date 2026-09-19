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

package com.learnify.api.config;

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.repository.UsuarioRepository;
import com.learnify.api.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void shouldLoadUserDetailsByEmail() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).email("user@example.com")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build();
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        
        var result = service.loadUserByUsername(usuario.getEmail());

        
        assertInstanceOf(CustomUserDetails.class, result);
        assertEquals(usuario.getEmail(), result.getUsername());

        
        verify(usuarioRepository).findByEmail(usuario.getEmail());
    }

    @Test
    void shouldThrowWhenEmailDoesNotExist() {
        
        when(usuarioRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("missing@example.com"));
        assertEquals("Usuário não encontrado!", exception.getMessage());

        
        verify(usuarioRepository).findByEmail("missing@example.com");
    }
}