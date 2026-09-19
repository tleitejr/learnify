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

package com.learnify.api.security;

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomUserDetailsTest {

    @Test
    void shouldExposeUserIdentityCredentialsAndRole() {
        
        UUID userId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).email("user@example.com").senha("hash").papel(PapelUsuario.ESTUDANTE).build();
        CustomUserDetails details = new CustomUserDetails(usuario);

        
        
        assertEquals(userId, details.getId());
        assertEquals("user@example.com", details.getEmail());
        assertEquals(usuario, details.getUsuario());
        assertEquals("hash", details.getPassword());
        assertEquals("user@example.com", details.getUsername());
        assertEquals("ROLE_ESTUDANTE", details.getAuthorities().iterator().next().getAuthority());
        assertTrue(details.isAccountNonExpired());
        assertTrue(details.isAccountNonLocked());
        assertTrue(details.isCredentialsNonExpired());
        assertTrue(details.isEnabled());
    }
}