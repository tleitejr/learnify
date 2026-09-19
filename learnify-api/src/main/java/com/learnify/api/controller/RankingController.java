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

import com.learnify.api.dto.response.RankingResponseDTO;
import com.learnify.api.service.RankingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for retrieving user ranking/leaderboard data.
 *
 * <p>This controller provides a paginated endpoint to view the top users
 * based on their total points, level, or other performance metrics.
 * It encourages gamification and friendly competition among learners.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Global ranking sorted by total points descending</li>
 *     <li>Pagination support (page, size, sort)</li>
 *     <li>Each entry contains user name, level, points, and rank position</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    /**
     * Returns a paginated ranking of users ordered by total score.
     *
     * <p>Clients can specify page number, page size, and sorting criteria
     * (e.g., sorting by points in descending order) via the Pageable parameter.
     *
     * @param pageable pagination information (page, size, sort)
     * @return a page of ranking DTOs, each containing user info and rank position
     */
    @GetMapping
    public ResponseEntity<Page<RankingResponseDTO>> ranking(Pageable pageable) {
        return ResponseEntity.ok(rankingService.obterRanking(pageable));
    }
}