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

import com.learnify.api.config.JwtService;
import com.learnify.api.config.SecurityConfig;
import com.learnify.api.domain.entity.RegistroAcesso;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.dto.request.LoginRequestDTO;
import com.learnify.api.dto.request.UsuarioRequestDTO;
import com.learnify.api.dto.response.LoginResponseDTO;
import com.learnify.api.dto.response.LogoutResponseDTO;
import com.learnify.api.exception.BusinessException;
import com.learnify.api.exception.ResourceNotFoundException;
import com.learnify.api.exception.UnauthorizedException;
import com.learnify.api.repository.RegistroAcessoRepository;
import com.learnify.api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RegistroAcessoRepository registroAcessoRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthService authService;

    private Usuario testUsuario;
    private LoginRequestDTO loginRequest;

    @BeforeEach
    void setUp() {
        testUsuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("João Silva")
                .email("joao@example.com")
                .senha("hashedPassword123")
                .papel(PapelUsuario.ESTUDANTE)
                .nivel(1)
                .pontuacaoTotal(0)
                .build();

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("joao@example.com");
        loginRequest.setSenha("password123");
    }

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void testLoginSuccess() {
        
        when(usuarioRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(testUsuario));
        when(passwordEncoder.matches(loginRequest.getSenha(), testUsuario.getSenha()))
                .thenReturn(true);
        when(jwtService.gerarToken(testUsuario.getEmail()))
                .thenReturn("valid-jwt-token");
        when(registroAcessoRepository.save(any(RegistroAcesso.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        
        LoginResponseDTO response = authService.login(loginRequest);

        
        assertNotNull(response);
        assertEquals("valid-jwt-token", response.token());
        assertEquals("Bearer", response.type());
        assertEquals(3600, response.expiresIn());
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getSenha(), testUsuario.getSenha());
        verify(jwtService).gerarToken(testUsuario.getEmail());
        verify(registroAcessoRepository).save(any(RegistroAcesso.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user email not found")
    void testLoginUserNotFound() {
        
        when(usuarioRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

         
        assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest),
                "Should throw ResourceNotFoundException when email not found");
        verify(usuarioRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when password is incorrect")
    void testLoginInvalidPassword() {
        
        when(usuarioRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(testUsuario));
        when(passwordEncoder.matches(loginRequest.getSenha(), testUsuario.getSenha()))
                .thenReturn(false);

         
        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest),
                "Should throw UnauthorizedException when password is incorrect");
        verify(registroAcessoRepository, never()).save(any(RegistroAcesso.class));
    }

    @Test
    @DisplayName("Should signup successfully with valid data")
    void testSignupSuccess() {
        
        UsuarioRequestDTO signupRequest = new UsuarioRequestDTO("João Silva", "newuser@example.com", "password123", null);
        when(usuarioRepository.existsByEmail(signupRequest.email())).thenReturn(false);
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(signupRequest.senha())).thenReturn("hashedPassword");
        
        Usuario savedUsuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("João Silva")
                .email("newuser@example.com")
                .senha("hashedPassword")
                .papel(PapelUsuario.ESTUDANTE)
                .nivel(1)
                .pontuacaoTotal(0)
                .build();
        
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUsuario);
        when(usuarioRepository.findByEmail(signupRequest.email())).thenReturn(Optional.of(savedUsuario));
        when(passwordEncoder.matches(signupRequest.senha(), savedUsuario.getSenha())).thenReturn(true);
        when(jwtService.gerarToken(savedUsuario.getEmail())).thenReturn("jwt-token");
        when(registroAcessoRepository.save(any(RegistroAcesso.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        
        LoginResponseDTO response = authService.signup(signupRequest);

        
        assertNotNull(response);
        assertEquals("jwt-token", response.token());
        assertEquals("Bearer", response.type());
        verify(usuarioRepository).existsByEmail(signupRequest.email());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when email already exists")
    void testSignupEmailAlreadyExists() {
        
        UsuarioRequestDTO signupRequest = new UsuarioRequestDTO("João Silva", "joao@example.com", "password123", null);
        when(usuarioRepository.existsByEmail(signupRequest.email())).thenReturn(true);

         
        assertThrows(BusinessException.class, () -> authService.signup(signupRequest),
                "Should throw BusinessException when email already exists");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Should logout successfully")
    void testLogoutSuccess() {
        
        RegistroAcesso registro = new RegistroAcesso();
        registro.setId(UUID.randomUUID());
        registro.setUsuario(testUsuario);
        registro.setDataLogin(LocalDateTime.now().minusHours(1));
        registro.setDataLogout(null);

        when(usuarioRepository.findByEmail(testUsuario.getEmail()))
                .thenReturn(Optional.of(testUsuario));
        when(registroAcessoRepository.findTopByUsuarioIdOrderByDataLoginDesc(testUsuario.getId()))
                .thenReturn(Optional.of(registro));
        when(registroAcessoRepository.save(any(RegistroAcesso.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        
        LogoutResponseDTO response = authService.logout(testUsuario);

        
        assertNotNull(response);
        assertEquals("Logout feito com sucesso!", response.message());
        verify(usuarioRepository).findByEmail(testUsuario.getEmail());
        verify(registroAcessoRepository).findTopByUsuarioIdOrderByDataLoginDesc(testUsuario.getId());
        verify(registroAcessoRepository).save(any(RegistroAcesso.class));
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when user already logged out")
    void testLogoutAlreadyLoggedOut() {
        
        RegistroAcesso registro = new RegistroAcesso();
        registro.setId(UUID.randomUUID());
        registro.setUsuario(testUsuario);
        registro.setDataLogin(LocalDateTime.now().minusHours(1));
        registro.setDataLogout(LocalDateTime.now());

        when(usuarioRepository.findByEmail(testUsuario.getEmail()))
                .thenReturn(Optional.of(testUsuario));
        when(registroAcessoRepository.findTopByUsuarioIdOrderByDataLoginDesc(testUsuario.getId()))
                .thenReturn(Optional.of(registro));

         
        assertThrows(UnauthorizedException.class, () -> authService.logout(testUsuario),
                "Should throw UnauthorizedException when user already logged out");
        verify(registroAcessoRepository, never()).save(any(RegistroAcesso.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when access log not found for logout")
    void testLogoutAccessLogNotFound() {
        
        when(usuarioRepository.findByEmail(testUsuario.getEmail()))
                .thenReturn(Optional.of(testUsuario));
        when(registroAcessoRepository.findTopByUsuarioIdOrderByDataLoginDesc(testUsuario.getId()))
                .thenReturn(Optional.empty());

         
        assertThrows(ResourceNotFoundException.class, () -> authService.logout(testUsuario),
                "Should throw ResourceNotFoundException when access log not found");
    }

        @Test
        void shouldThrowWhenLogoutUserIsNotFound() {
                
                when(usuarioRepository.findByEmail(testUsuario.getEmail())).thenReturn(Optional.empty());

                
                assertThrows(ResourceNotFoundException.class, () -> authService.logout(testUsuario));

                
                verify(registroAcessoRepository, never()).findTopByUsuarioIdOrderByDataLoginDesc(any());
        }

        @Test
        void shouldReturnFailureMessageWhenDeleteServiceReturnsFalse() {
                
                when(usuarioService.delete(testUsuario)).thenReturn(false);

                
                String result = authService.delete(testUsuario);

                
                assertEquals("Erro ao deletar usuário!", result);

                
                verify(usuarioService).delete(testUsuario);
        }
}
