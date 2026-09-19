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

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.dto.response.EstatisticasUsuarioResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.ConteudoUsuarioService;
import com.learnify.api.service.UsuarioService;
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
class UsuarioControllerTest {
    @Mock private UsuarioService usuarioService;
    @Mock private ConteudoUsuarioService conteudoUsuarioService;
    @Mock private CustomUserDetails userDetails;
    @InjectMocks private UsuarioController controller;

    @Test
    void shouldReturnAuthenticatedUserProfile() {
        
        UUID userId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).nome("Ana").email("ana@example.com").nivel(2).pontuacaoTotal(40).build();
        when(userDetails.getId()).thenReturn(userId);
        when(userDetails.getUsuario()).thenReturn(usuario);

        
        var result = controller.me(userDetails);

        
        assertEquals("Ana", result.nome());
        assertEquals(40, result.pontuacaoTotal());

        
        verify(conteudoUsuarioService).loadConteudosUsuario(userId);
    }

    @Test
    void shouldReturnAchievementsAndStatistics() {
        
        Usuario usuario = new Usuario();
        when(userDetails.getId()).thenReturn(UUID.randomUUID());
        when(userDetails.getUsuario()).thenReturn(usuario);
        EstatisticasUsuarioResponseDTO statistics = new EstatisticasUsuarioResponseDTO(0, 0, 0, java.math.BigDecimal.ZERO, 0, java.math.BigDecimal.ZERO, 1, "Regular");
        when(usuarioService.listarConquistas(userDetails.getId())).thenReturn(List.of());
        when(usuarioService.obterEstatisticas(usuario)).thenReturn(statistics);

        
        var achievements = controller.minhasConquistas(userDetails);
        var result = controller.estatisticas(userDetails);

        
        assertEquals(0, achievements.getBody().size());
        assertEquals(statistics, result.getBody());

        
        verify(usuarioService).listarConquistas(userDetails.getId());
        verify(usuarioService).obterEstatisticas(usuario);
    }
}
