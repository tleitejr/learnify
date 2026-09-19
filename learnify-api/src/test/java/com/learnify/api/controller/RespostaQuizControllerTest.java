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
import com.learnify.api.dto.request.RespostaQuizRequestDTO;
import com.learnify.api.dto.response.ResultadoQuizResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RespostaQuizControllerTest {
    @Mock private QuizService quizService;
    @Mock private CustomUserDetails userDetails;
    @InjectMocks private RespostaQuizController controller;

    @Test
    void shouldDelegateQuizAnswer() {
        
        UUID questionId = UUID.randomUUID();
        UUID alternativeId = UUID.randomUUID();
        Usuario usuario = new Usuario();
        RespostaQuizRequestDTO request = new RespostaQuizRequestDTO(questionId, alternativeId);
        ResultadoQuizResponseDTO response = new ResultadoQuizResponseDTO(true, 10, 10, 1, null);
        when(userDetails.getUsuario()).thenReturn(usuario);
        when(quizService.responder(usuario, questionId, alternativeId)).thenReturn(response);

        
        var result = controller.responder(request, userDetails);

        
        assertSame(response, result.getBody());

        
        verify(quizService).responder(usuario, questionId, alternativeId);
    }
}
