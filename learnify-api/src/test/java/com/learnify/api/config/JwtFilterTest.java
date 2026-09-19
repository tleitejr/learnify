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

import com.learnify.api.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private CustomUserDetails userDetails;

    @InjectMocks
    private JwtFilter filter;

    @BeforeEach
    void clearSecurityStateBeforeTest() {
        SecurityContextHolder.clearContext();
        MDC.clear();
    }

    @AfterEach
    void clearSecurityState() {
        SecurityContextHolder.clearContext();
        MDC.clear();
    }

    @Test
    void shouldAuthenticateRequestWithValidBearerToken() throws Exception {
        
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtService.validarToken("valid-token")).thenReturn(true);
        when(jwtService.extrairEmail("valid-token")).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(List.of());

        
        filter.doFilterInternal(request, response, filterChain);

        
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertEquals("user@example.com", MDC.get("userEmail"));

        
        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("user@example.com");
    }

    @Test
    void shouldContinueWithoutAuthenticationWhenHeaderIsMissingOrInvalid() throws Exception {
        
        when(request.getHeader("Authorization")).thenReturn(null);

        
        filter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        
        verify(jwtService, never()).validarToken(org.mockito.ArgumentMatchers.anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldIgnoreInvalidBearerToken() throws Exception {
        
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(jwtService.validarToken("invalid-token")).thenReturn(false);

        
        filter.doFilterInternal(request, response, filterChain);

        
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        
        verify(userDetailsService, never()).loadUserByUsername(org.mockito.ArgumentMatchers.anyString());
        verify(filterChain).doFilter(request, response);
    }
}