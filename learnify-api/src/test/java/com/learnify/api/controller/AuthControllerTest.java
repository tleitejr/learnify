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

import org.junit.jupiter.api.Test;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.dto.request.LoginRequestDTO;
import com.learnify.api.dto.request.UsuarioRequestDTO;
import com.learnify.api.dto.response.LoginResponseDTO;
import com.learnify.api.dto.response.LogoutResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.AuthService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void shouldDelegateLogin() {
        
        LoginRequestDTO request = new LoginRequestDTO();
        LoginResponseDTO response = new LoginResponseDTO("token", "Bearer", 3600);
        when(authService.login(request)).thenReturn(response);

        
        ResponseEntity<LoginResponseDTO> result = authController.login(request);

        
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCode().value());

        
        verify(authService).login(request);
    }

    @Test
    void shouldDelegateSignupLogoutAndDelete() {
        
        Usuario usuario = Usuario.builder().id(UUID.randomUUID()).email("user@example.com").papel(PapelUsuario.ESTUDANTE).build();
        CustomUserDetails user = new CustomUserDetails(usuario);
        UsuarioRequestDTO signup = new UsuarioRequestDTO("User", "user@example.com", "secret", null);
        LogoutResponseDTO logout = new LogoutResponseDTO("ok", LocalDateTime.now());
        when(authService.signup(signup)).thenReturn(new LoginResponseDTO("token", "Bearer", 3600));
        when(authService.logout(usuario)).thenReturn(logout);
        when(authService.delete(usuario)).thenReturn("deleted");

        
        ResponseEntity<LoginResponseDTO> signupResult = authController.signup(signup);
        ResponseEntity<LogoutResponseDTO> logoutResult = authController.logout(user);
        ResponseEntity<String> deleteResult = authController.delete(user);

        
        assertEquals("token", signupResult.getBody().token());
        assertEquals(logout, logoutResult.getBody());
        assertEquals("deleted", deleteResult.getBody());

        
        verify(authService).signup(signup);
        verify(authService).logout(usuario);
        verify(authService).delete(usuario);
    }
}
