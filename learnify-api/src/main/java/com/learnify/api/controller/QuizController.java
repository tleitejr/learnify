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

import com.learnify.api.dto.response.QuestaoResponseDTO;
import com.learnify.api.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for retrieving quiz questions related to a specific content.
 *
 * <p>This controller provides a public (or semi-public) endpoint to fetch
 * quiz data for a given piece of educational content. Typically used to
 * present questions to the user before submitting answers.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Load all questions for a content</li>
 *     <li>Include answer alternatives for each question</li>
 *     <li>Returns metadata such as question text and alternative IDs</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/conteudos")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Retrieves the complete quiz (list of questions with alternatives) for a given content.
     *
     * <p>The method does not require authentication in this signature, but the service
     * may later filter questions based on user progress.
     *
     * @param conteudoId the unique identifier of the content (module/lesson)
     * @return a list of question DTOs, each containing the question text and available alternatives
     */
    @GetMapping("/{conteudoId}/quiz")
    public List<QuestaoResponseDTO> carregarQuiz(@PathVariable UUID conteudoId) {
        return quizService.carregarQuiz(conteudoId);
    }
}