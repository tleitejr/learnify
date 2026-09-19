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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Service responsible for authentication and account lifecycle operations.
 *
 * <p>Provides login, signup, logout, and account deletion. During login, validates
 * credentials, records an access log, and issues a JWT token. Signup creates a new
 * user with an encoded password and default ESTUDANTE role, then authenticates it.
 * Logout completes the latest access log with logout time and session duration.
 *
 * <p>Key Features:
 * <ul>
 *     <li>User authentication with email and password</li>
 *     <li>New account registration</li>
 *     <li>Access log recording for login and logout</li>
 *     <li>Authenticated account deletion</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final RegistroAcessoRepository registroAcessoRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityConfig securityConfig;

    public AuthService(
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService,
            RegistroAcessoRepository registroAcessoRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            SecurityConfig securityConfig
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.registroAcessoRepository = registroAcessoRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.securityConfig = securityConfig;
    }
    
    /**
     * Authenticates a user with email and password.
     *
     * <p>Validates the credentials, creates an access log entry, and generates
     * a JWT token for the authenticated session.
     *
     * @param dto the login request containing email and password
     * @return a response DTO with the access token, token type, and expiration time
     * @throws ResourceNotFoundException if the email is not registered
     * @throws UnauthorizedException if the password does not match
     */
    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("Iniciando login... | Email do usuário: {}", dto.getEmail());

        var usuario = usuarioRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new UnauthorizedException("Credenciais inválidas!");
        }

        RegistroAcesso registro = new RegistroAcesso();
        registro.setUsuario(usuario);
        registro.setDataLogin(LocalDateTime.now());
        
        registroAcessoRepository.save(registro);

        log.info("Login salvo com sucesso! | ID do usuário: {} | Data do Login: {}",
                registro.getUsuario().getId(),
                registro.getDataLogin()
        );

        String token = jwtService.gerarToken(usuario.getEmail());
        return new LoginResponseDTO(
                token,
                "Bearer",
                3600
        );
    }

    /**
     * Registers a new user account.
     *
     * <p>Validates that the email is not already in use, encodes the password,
     * creates the user with default role (ESTUDANTE), and automatically logs them in.
     *
     * @param dto the signup request containing name, email, and password
     * @return a login response DTO with the JWT token for the new user
     * @throws BusinessException if the email is already registered
     */
    public LoginResponseDTO signup(UsuarioRequestDTO dto) {
        log.info("Iniciando cadastro..., email: {}.", dto.email());

        if (usuarioRepository.existsByEmail(dto.email())) {
            log.warn("Email {} já cadastrado!", dto.email());
            throw new BusinessException("Email já cadastrado!");
        }

        String senhaEncriptada = securityConfig.passwordEncoder().encode(dto.senha());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(senhaEncriptada);
        usuario.setPapel(PapelUsuario.ESTUDANTE);

        usuarioRepository.save(usuario);

        log.info("Usuário cadastrado com sucesso! | ID do usuário: {}", usuario.getId());

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail(dto.email());
        request.setSenha(dto.senha());

        return login(request);
    }

    /**
     * Logs out the authenticated user.
     *
     * <p>Updates the last access log entry with logout time and session duration.
     * Throws an exception if the user has already logged out (prevents duplicate logout).
     *
     * @param user the authenticated user entity
     * @return a response DTO confirming successful logout with timestamp
     * @throws ResourceNotFoundException if the user or their last access log is not found
     * @throws UnauthorizedException if the user already performed logout
     */
    public LogoutResponseDTO logout(Usuario user) {
        log.info("Iniciando logout... | Email do usuário: {}", user.getEmail());

        var usuario = usuarioRepository
                .findByEmail(user.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        RegistroAcesso registro = registroAcessoRepository
                .findTopByUsuarioIdOrderByDataLoginDesc(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        if (registro.getDataLogout() != null) {
            log.warn("Fraude detectada! | ID do usuário: {}", registro.getUsuario().getId());
            throw new UnauthorizedException("Usuário já fez logout");
        }

        registro.setDataLogout(LocalDateTime.now());
        registro.setDuracaoSegundos(
                Duration.between(
                        registro.getDataLogin(),
                        LocalDateTime.now()
                ).getSeconds()
        );

        registroAcessoRepository.save(registro);

        log.info("Logout salvo com sucesso! | ID do usuário: {} | Data do Logout: {}",
                registro.getUsuario().getId(),
                registro.getDataLogout()
        );

        return new LogoutResponseDTO(
                "Logout feito com sucesso!",
                LocalDateTime.now()
        );
    }

    /**
     * Permanently deletes the authenticated user account and all associated data.
     *
     * <p>Delegates the deletion to {@link UsuarioService}, which removes all
     * related records (access logs, progress, scores, answers, achievements, etc.)
     * before deleting the user entity itself.
     *
     * @param usuario the authenticated user entity
     * @return a success message if deletion was completed, or an error message otherwise
     * @throws RuntimeException if an error occurs during the deletion of associated data
     */
    public String delete(Usuario usuario) {
        if (usuarioService.delete(usuario)) {
            return "Usuário deletado com sucesso!";
        } else {
            return "Erro ao deletar usuário!";
        }
    }
}