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

import com.learnify.api.service.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {
    @Mock private QuizService quizService;
    @InjectMocks private QuizController controller;

    @Test
    void shouldLoadQuizForContent() {
        
        UUID contentId = UUID.randomUUID();
        when(quizService.carregarQuiz(contentId)).thenReturn(List.of());

        
        List<?> result = controller.carregarQuiz(contentId);

        
        assertTrue(result.isEmpty());

        
        verify(quizService).carregarQuiz(contentId);
    }
}
