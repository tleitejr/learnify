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

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.dto.response.RankingResponseDTO;
import com.learnify.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RankingService rankingService;

    @Test
    void shouldMapUsersToRankingPage() {
        
        PageRequest pageable = PageRequest.of(0, 10);
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).nome("Ana").nivel(3).pontuacaoTotal(250).build();
        when(usuarioRepository.findAllByOrderByPontuacaoTotalDesc(pageable))
                .thenReturn(new PageImpl<>(List.of(usuario), pageable, 1));

        
        Page<RankingResponseDTO> result = rankingService.obterRanking(pageable);

        
        assertEquals(1, result.getTotalElements());
        assertEquals("Ana", result.getContent().get(0).nomeUsuario());
        assertEquals(3, result.getContent().get(0).nivel());
        assertEquals(250, result.getContent().get(0).pontuacaoTotal());

        
        verify(usuarioRepository).findAllByOrderByPontuacaoTotalDesc(pageable);
    }
}
