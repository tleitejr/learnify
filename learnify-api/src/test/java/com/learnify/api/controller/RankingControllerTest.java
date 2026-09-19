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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankingControllerTest {
    @Mock private RankingService rankingService;
    @Mock private Pageable pageable;
    @Mock private Page<RankingResponseDTO> page;
    @InjectMocks private RankingController controller;

    @Test
    void shouldReturnRankingPage() {
        
        when(rankingService.obterRanking(pageable)).thenReturn(page);

        
        var result = controller.ranking(pageable);

        
        assertSame(page, result.getBody());

        
        verify(rankingService).obterRanking(pageable);
    }
}
