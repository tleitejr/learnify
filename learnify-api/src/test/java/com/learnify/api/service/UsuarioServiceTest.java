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
import com.learnify.api.domain.entity.UsuarioConquista;
import com.learnify.api.domain.entity.Conquista;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.domain.enums.TipoDesempenho;
import com.learnify.api.dto.response.ConquistaResponseDTO;
import com.learnify.api.dto.response.EstatisticasUsuarioResponseDTO;
import com.learnify.api.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService Tests")
class UsuarioServiceTest {

    @Mock
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Mock
    private RespostaUsuarioRepository respostaUsuarioRepository;

    @Mock
    private ConteudoUsuarioRepository conteudoUsuarioRepository;

    @Mock
    private RegistroAcessoRepository registroAcessoRepository;

    @Mock
    private PontuacaoRepository pontuacaoRepository;

    @Mock
    private ProgressoRepository progressoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario testUsuario;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        testUsuario = Usuario.builder()
                .id(usuarioId)
                .nome("João Silva")
                .email("joao@example.com")
                .senha("hashedPassword123")
                .papel(PapelUsuario.ESTUDANTE)
                .nivel(5)
                .pontuacaoTotal(500)
                .build();
    }

    @Test
    @DisplayName("Should list all achievements for user")
    void testListarConquistas() {
        
        Conquista conquista1 = new Conquista();
        conquista1.setId(UUID.randomUUID());
        conquista1.setTitulo("Primeiro Quiz");
        conquista1.setDescricao("Completou o primeiro quiz");

        Conquista conquista2 = new Conquista();
        conquista2.setId(UUID.randomUUID());
        conquista2.setTitulo("100 Pontos");
        conquista2.setDescricao("Alcançou 100 pontos");

        UsuarioConquista uc1 = new UsuarioConquista();
        uc1.setUsuario(testUsuario);
        uc1.setConquista(conquista1);

        UsuarioConquista uc2 = new UsuarioConquista();
        uc2.setUsuario(testUsuario);
        uc2.setConquista(conquista2);

        when(usuarioConquistaRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(uc1, uc2));

        
        List<ConquistaResponseDTO> result = usuarioService.listarConquistas(usuarioId);

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Primeiro Quiz", result.get(0).titulo());
        assertEquals("100 Pontos", result.get(1).titulo());
        verify(usuarioConquistaRepository).findByUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Should return empty list when user has no achievements")
    void testListarConquistasEmpty() {
        
        when(usuarioConquistaRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of());

        
        List<ConquistaResponseDTO> result = usuarioService.listarConquistas(usuarioId);

        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should calculate user statistics correctly")
    void testObterEstatisticas() {
        
        long totalAnswers = 100;
        long correctAnswers = 80;
        long incorrectAnswers = 20;
        double averagePoints = 9.5;

        when(respostaUsuarioRepository.countByUsuarioId(usuarioId))
                .thenReturn(totalAnswers);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId))
                .thenReturn(correctAnswers);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId))
                .thenReturn(incorrectAnswers);
        when(respostaUsuarioRepository.mediaPontos(usuarioId))
                .thenReturn(averagePoints);

        
        EstatisticasUsuarioResponseDTO result = usuarioService.obterEstatisticas(testUsuario);

        
        assertNotNull(result);
        assertEquals(totalAnswers, result.totalRespostas());
        assertEquals(correctAnswers, result.acertos());
        assertEquals(incorrectAnswers, result.erros());
        assertEquals(0, BigDecimal.valueOf(80.0).compareTo(result.percentual()));
        assertEquals(500, result.pontuacaoTotal());
        assertEquals(5, result.nivel());
        assertEquals(TipoDesempenho.EXCELENTE.getMessage(), result.desempenho());
    }

    @Test
    @DisplayName("Should classify performance as MUITO_BOM for 75-79% accuracy")
    void testPerformanceClassificationMuitoBom() {
        
        when(respostaUsuarioRepository.countByUsuarioId(usuarioId))
                .thenReturn(100L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId))
                .thenReturn(75L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId))
                .thenReturn(25L);
        when(respostaUsuarioRepository.mediaPontos(usuarioId))
                .thenReturn(9.0);

        
        EstatisticasUsuarioResponseDTO result = usuarioService.obterEstatisticas(testUsuario);

        
        assertEquals(TipoDesempenho.MUITO_BOM.getMessage(), result.desempenho());
    }

    @Test
    @DisplayName("Should classify performance as BOM for 60-74% accuracy")
    void testPerformanceClassificationBom() {
        
        when(respostaUsuarioRepository.countByUsuarioId(usuarioId))
                .thenReturn(100L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId))
                .thenReturn(65L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId))
                .thenReturn(35L);
        when(respostaUsuarioRepository.mediaPontos(usuarioId))
                .thenReturn(7.0);

        
        EstatisticasUsuarioResponseDTO result = usuarioService.obterEstatisticas(testUsuario);

        
        assertEquals(TipoDesempenho.BOM.getMessage(), result.desempenho());
    }

    @Test
    @DisplayName("Should classify performance as REGULAR for 0% accuracy")
    void testPerformanceClassificationZeroPercent() {
        
        when(respostaUsuarioRepository.countByUsuarioId(usuarioId))
                .thenReturn(0L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId))
                .thenReturn(0L);
        when(respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId))
                .thenReturn(0L);
        when(respostaUsuarioRepository.mediaPontos(usuarioId))
                .thenReturn(0.0);

        
        EstatisticasUsuarioResponseDTO result = usuarioService.obterEstatisticas(testUsuario);

        
        assertEquals(TipoDesempenho.REGULAR.getMessage(), result.desempenho());
    }

        @Test
        void shouldClassifyLowerPerformanceBoundaries() {
                
                when(respostaUsuarioRepository.mediaPontos(usuarioId)).thenReturn(1.0);
                when(respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId)).thenReturn(99L);

                
                when(respostaUsuarioRepository.countByUsuarioId(usuarioId)).thenReturn(100L);
                when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId)).thenReturn(30L);
                assertEquals(TipoDesempenho.RUIM.getMessage(), usuarioService.obterEstatisticas(testUsuario).desempenho());

                when(respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId)).thenReturn(1L);
                assertEquals(TipoDesempenho.MUITO_RUIM.getMessage(), usuarioService.obterEstatisticas(testUsuario).desempenho());

                
                verify(respostaUsuarioRepository, org.mockito.Mockito.atLeastOnce()).mediaPontos(usuarioId);
        }

        @Test
        void shouldReturnFalseAndAvoidDeletingUserWhenRelatedDeletionFails() {
                
                doThrow(new IllegalStateException("database error")).when(usuarioConquistaRepository).deleteByUsuarioId(usuarioId);

                
                assertThrows(RuntimeException.class, () -> usuarioService.delete(testUsuario));

                
                verify(usuarioRepository, never()).delete(testUsuario);
        }

    @Test
    @DisplayName("Should successfully delete user and all associated data")
    void testDeleteUserSuccess() {
        
        doNothing().when(usuarioConquistaRepository).deleteByUsuarioId(usuarioId);
        doNothing().when(respostaUsuarioRepository).deleteByUsuarioId(usuarioId);
        doNothing().when(conteudoUsuarioRepository).deleteByUsuarioId(usuarioId);
        doNothing().when(registroAcessoRepository).deleteByUsuarioId(usuarioId);
        doNothing().when(pontuacaoRepository).deleteByUsuarioId(usuarioId);
        doNothing().when(progressoRepository).deleteByUsuarioId(usuarioId);

        
        boolean result = usuarioService.delete(testUsuario);

        
        assertTrue(result);
        verify(usuarioConquistaRepository).deleteByUsuarioId(usuarioId);
        verify(respostaUsuarioRepository).deleteByUsuarioId(usuarioId);
        verify(conteudoUsuarioRepository).deleteByUsuarioId(usuarioId);
        verify(registroAcessoRepository).deleteByUsuarioId(usuarioId);
        verify(pontuacaoRepository).deleteByUsuarioId(usuarioId);
        verify(progressoRepository).deleteByUsuarioId(usuarioId);
        verify(usuarioRepository).delete(testUsuario);
    }
}
