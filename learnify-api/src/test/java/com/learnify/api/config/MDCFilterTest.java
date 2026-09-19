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

package com.learnify.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MDCFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain filterChain;

    private final MDCFilter filter = new MDCFilter();

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    void shouldAddRequestContextDuringChainAndClearItAfterwards() throws Exception {
        
        when(request.getRequestURI()).thenReturn("/api/v1/health");
        doAnswer(invocation -> {
            assertNotNull(MDC.get("requestId"));
            assertEquals("/api/v1/health", MDC.get("path"));
            return null;
        }).when(filterChain).doFilter(request, response);

        
        filter.doFilter(request, response, filterChain);

        
        assertNull(MDC.get("requestId"));
        assertNull(MDC.get("path"));

        
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldClearRequestContextWhenChainFails() throws Exception {
        
        when(request.getRequestURI()).thenReturn("/failure");
        doAnswer(invocation -> {
            throw new IllegalStateException("downstream failure");
        }).when(filterChain).doFilter(request, response);

        
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                () -> filter.doFilter(request, response, filterChain));
        assertNull(MDC.get("requestId"));
        assertNull(MDC.get("path"));

        
        verify(filterChain).doFilter(request, response);
    }
}