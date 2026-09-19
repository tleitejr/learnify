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

import com.learnify.api.domain.entity.Alternativa;
import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.domain.entity.ConteudoUsuario;
import com.learnify.api.domain.entity.Questao;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.dto.response.QuestaoResponseDTO;
import com.learnify.api.dto.response.ResultadoQuizResponseDTO;
import com.learnify.api.exception.BusinessException;
import com.learnify.api.exception.ResourceNotFoundException;
import com.learnify.api.repository.AlternativaRepository;
import com.learnify.api.repository.ConteudoUsuarioRepository;
import com.learnify.api.repository.QuestaoRepository;
import com.learnify.api.repository.PontuacaoRepository;
import com.learnify.api.repository.RespostaUsuarioRepository;
import com.learnify.api.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuestaoRepository questaoRepository;

    @Mock
    private ConteudoUsuarioRepository conteudoUsuarioRepository;

    @Mock
    private AlternativaRepository alternativaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PontuacaoRepository pontuacaoRepository;

    @Mock
    private ProgressoService progressoService;

    @Mock
    private GamificacaoService gamificacaoService;

    @Mock
    private RespostaUsuarioRepository respostaUsuarioRepository;

    @InjectMocks
    private QuizService quizService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldProcessCorrectAnswerAndCompleteContent() {
        
        UUID userId = UUID.randomUUID();
        UUID contentId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).email("user@example.com").pontuacaoTotal(90).nivel(1).build();
        Conteudo conteudo = Conteudo.builder().id(contentId).titulo("Java").build();
        Questao questao = Questao.builder().id(questionId).conteudo(conteudo).enunciado("2 + 2?").numero(1).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).questao(questao).correta(true).descricao("4").build();
        ConteudoUsuario conteudoUsuario = ConteudoUsuario.builder().usuario(usuario).conteudo(conteudo).build();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.of(alternativa));
        when(respostaUsuarioRepository.existsByUsuarioIdAndQuestaoId(userId, questionId)).thenReturn(false);
        when(gamificacaoService.verificarConquistas(usuario)).thenReturn(Optional.of("Aprendiz"));
        when(questaoRepository.findByConteudoId(contentId)).thenReturn(List.of(questao));
        when(respostaUsuarioRepository.findByUsuarioIdAndConteudoId(userId, contentId)).thenReturn(List.of(new com.learnify.api.domain.entity.RespostaUsuario()));
        when(conteudoUsuarioRepository.findByUsuarioAndConteudo(usuario, conteudo)).thenReturn(Optional.of(conteudoUsuario));

        
        ResultadoQuizResponseDTO result = quizService.responder(usuario, questionId, alternativeId);

        
        assertTrue(result.correta());
        assertEquals(10, result.pontosGanhos());
        assertEquals(100, result.pontuacaoTotal());
        assertEquals(2, result.nivel());
        assertEquals("Aprendiz", result.conquistaDesbloqueada());
        assertTrue(conteudoUsuario.getConcluido());

        
        verify(respostaUsuarioRepository).save(any());
        verify(pontuacaoRepository).save(any());
        verify(usuarioRepository).save(usuario);
        verify(progressoService).atualizarProgresso(userId, contentId, 10);
        verify(conteudoUsuarioRepository).save(conteudoUsuario);
    }

    @Test
    void shouldProcessIncorrectAnswerWithoutAwardingPoints() {
        
        UUID userId = UUID.randomUUID();
        UUID contentId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).email("user@example.com").pontuacaoTotal(20).nivel(1).build();
        Conteudo conteudo = Conteudo.builder().id(contentId).build();
        Questao questao = Questao.builder().id(questionId).conteudo(conteudo).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).questao(questao).correta(false).build();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.of(alternativa));
        when(respostaUsuarioRepository.existsByUsuarioIdAndQuestaoId(userId, questionId)).thenReturn(false);
        when(gamificacaoService.verificarConquistas(usuario)).thenReturn(Optional.empty());
        when(questaoRepository.findByConteudoId(contentId)).thenReturn(List.of(questao));
        when(respostaUsuarioRepository.findByUsuarioIdAndConteudoId(userId, contentId)).thenReturn(List.of());

        
        ResultadoQuizResponseDTO result = quizService.responder(usuario, questionId, alternativeId);

        
        assertFalse(result.correta());
        assertEquals(0, result.pontosGanhos());
        assertEquals(20, result.pontuacaoTotal());
        assertEquals(1, result.nivel());
        assertNull(result.conquistaDesbloqueada());

        
        verify(respostaUsuarioRepository).save(any());
        verify(pontuacaoRepository, never()).save(any());
        verify(usuarioRepository, never()).save(any());
        verify(conteudoUsuarioRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenAlternativeDoesNotExist() {
        
        UUID alternativeId = UUID.randomUUID();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.empty());

        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> quizService.responder(Usuario.builder().id(UUID.randomUUID()).build(), UUID.randomUUID(), alternativeId));

        
        assertEquals("Alternativa não encontrada!", exception.getMessage());

        
        verify(respostaUsuarioRepository, never()).save(any());
    }

    @Test
    void shouldRejectQuestionAlreadyAnswered() {
        
        UUID userId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).build();
        Questao questao = Questao.builder().id(questionId).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).questao(questao).build();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.of(alternativa));
        when(respostaUsuarioRepository.existsByUsuarioIdAndQuestaoId(userId, questionId)).thenReturn(true);

        
        BusinessException exception = assertThrows(BusinessException.class,
                () -> quizService.responder(usuario, questionId, alternativeId));

        
        assertEquals("Questão já respondida!", exception.getMessage());

        
        verify(respostaUsuarioRepository, never()).save(any());
        verify(gamificacaoService, never()).verificarConquistas(any());
    }

    @Test
    void shouldLoadQuizQuestionsAndAlternatives() {
        
        UUID contentId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Questao questao = Questao.builder().id(questionId).enunciado("Question").numero(1).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).descricao("Answer").questao(questao).build();
        when(questaoRepository.findByConteudoId(contentId)).thenReturn(List.of(questao));
        when(alternativaRepository.findByQuestaoId(questionId)).thenReturn(List.of(alternativa));

        
        List<QuestaoResponseDTO> result = quizService.carregarQuiz(contentId);

        
        assertEquals(1, result.size());
        assertSame(questionId, result.get(0).id());
        assertEquals("Answer", result.get(0).alternativas().get(0).descricao());

        
        verify(questaoRepository).findByConteudoId(contentId);
        verify(alternativaRepository).findByQuestaoId(questionId);
    }

    @Test
    void shouldLeaveContentIncompleteWhenNotAllQuestionsAreAnswered() {
        
        UUID userId = UUID.randomUUID();
        UUID contentId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).email("user@example.com").build();
        Conteudo conteudo = Conteudo.builder().id(contentId).build();
        Questao currentQuestion = Questao.builder().id(questionId).conteudo(conteudo).build();
        Questao unansweredQuestion = Questao.builder().id(UUID.randomUUID()).conteudo(conteudo).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).questao(currentQuestion).correta(false).build();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.of(alternativa));
        when(respostaUsuarioRepository.existsByUsuarioIdAndQuestaoId(userId, questionId)).thenReturn(false);
        when(gamificacaoService.verificarConquistas(usuario)).thenReturn(Optional.empty());
        when(questaoRepository.findByConteudoId(contentId)).thenReturn(List.of(currentQuestion, unansweredQuestion));
        when(respostaUsuarioRepository.findByUsuarioIdAndConteudoId(userId, contentId)).thenReturn(List.of(new com.learnify.api.domain.entity.RespostaUsuario()));

        
        quizService.responder(usuario, questionId, alternativeId);

        
        assertFalse(usuario.getPontuacaoTotal() > 0);

        
        verify(conteudoUsuarioRepository, never()).findByUsuarioAndConteudo(any(), any());
        verify(conteudoUsuarioRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCompletedContentAssociationDoesNotExist() {
        
        UUID userId = UUID.randomUUID();
        UUID contentId = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).email("user@example.com").build();
        Conteudo conteudo = Conteudo.builder().id(contentId).build();
        Questao questao = Questao.builder().id(questionId).conteudo(conteudo).build();
        Alternativa alternativa = Alternativa.builder().id(alternativeId).questao(questao).correta(false).build();
        setAuthenticatedUser("user@example.com");
        when(alternativaRepository.findById(alternativeId)).thenReturn(Optional.of(alternativa));
        when(respostaUsuarioRepository.existsByUsuarioIdAndQuestaoId(userId, questionId)).thenReturn(false);
        when(gamificacaoService.verificarConquistas(usuario)).thenReturn(Optional.empty());
        when(questaoRepository.findByConteudoId(contentId)).thenReturn(List.of(questao));
        when(respostaUsuarioRepository.findByUsuarioIdAndConteudoId(userId, contentId)).thenReturn(List.of(new com.learnify.api.domain.entity.RespostaUsuario()));
        when(conteudoUsuarioRepository.findByUsuarioAndConteudo(usuario, conteudo)).thenReturn(Optional.empty());

        
        assertThrows(IllegalStateException.class, () -> quizService.responder(usuario, questionId, alternativeId));

        
        verify(conteudoUsuarioRepository).findByUsuarioAndConteudo(usuario, conteudo);
        verify(conteudoUsuarioRepository, never()).save(any());
    }

    private void setAuthenticatedUser(String email) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, null, List.of())
        );
    }
}
