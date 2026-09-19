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

import com.learnify.api.dto.request.RespostaQuizRequestDTO;
import com.learnify.api.dto.response.ResultadoQuizResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for processing quiz answers submitted by the user.
 *
 * <p>This controller handles the submission of a user's answer to a specific
 * quiz question, evaluates its correctness, updates the user's score and
 * progress, and returns the immediate result.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Answer validation against the correct alternative</li>
 *     <li>Score and XP update for the user</li>
 *     <li>Immediate feedback (correct/incorrect, points earned)</li>
 *     <li>Unlock next content or achievement if applicable</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/quiz")
public class RespostaQuizController {

    @Autowired
    QuizService quizService;

    /**
     * Processes a user's answer to a quiz question.
     *
     * <p>The method validates the submitted alternative, updates the user's
     * statistics, records the answer history, and returns the result
     * (correct/incorrect, points awarded, etc.).
     *
     * @param dto the request containing questionId and selected alternativeId
     * @param userDetails the authenticated user details
     * @return a DTO with the quiz result (correctness, earned points, feedback message)
     */
    @PostMapping("/responder")
    public ResponseEntity<ResultadoQuizResponseDTO> responder(
            @RequestBody RespostaQuizRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        return ResponseEntity.ok(
                quizService.responder(
                        userDetails.getUsuario(),
                        dto.questaoId(),
                        dto.alternativaSelecionadaId()
                )
        );
    }
}