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

import com.learnify.api.dto.request.LoginRequestDTO;
import com.learnify.api.dto.request.UsuarioRequestDTO;
import com.learnify.api.dto.response.LoginResponseDTO;
import com.learnify.api.dto.response.LogoutResponseDTO;
import com.learnify.api.security.CustomUserDetails;
import com.learnify.api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller responsible for user login, registration, and logout.
 *
 * <p>This controller provides public endpoints for creating new accounts and
 * authenticating existing users, as well as a protected logout endpoint.
 * Authentication tokens (JWT) are issued upon successful login or signup.
 *
 * <p>Key Features:
 * <ul>
 *     <li>User login with email and password</li>
 *     <li>New user registration (signup)</li>
 *     <li>Secure logout (token invalidation)</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user with email and password.
     *
     * <p>If credentials are valid, a JWT token is generated and returned along
     * with basic user information.
     *
     * @param dto the login request containing email and password
     * @return a response DTO with the access token and user profile data
     * @throws com.learnify.api.exception.ResourceNotFoundException if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    /**
     * Registers a new user on the platform.
     *
     * <p>Validates the input (e.g., unique email, strong password), creates the user,
     * and automatically authenticates them, returning a valid JWT token.
     *
     * @param dto the signup request containing name, email, and password
     * @return a response DTO with the access token and the newly created user data
     * @throws com.learnify.api.exception.BusinessException if email is already registered
     */
    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDTO> signup(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(authService.signup(dto));
    }

    /**
     * Logs out the currently authenticated user.
     *
     * <p>Invalidates the user's session or JWT token (depending on the implementation)
     * to prevent further access using the same token.
     *
     * @param user the authenticated user details from the security context
     * @return a response DTO confirming the successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(authService.logout(user.getUsuario()));
    }

    /**
     * Permanently deletes the currently authenticated user account and all associated data.
     *
     * <p>This operation removes the user's profile, access logs, progress, scores,
     * answered questions, content interactions, and achievements. The action is
     * irreversible and requires the user to be properly authenticated.
     *
     * <p>In case of failure during the deletion of related records, a runtime
     * exception is thrown and the transaction is rolled back.
     *
     * @param user the authenticated user details from the security context
     * @return a confirmation message indicating successful deletion, or an error
     *         message if the deletion could not be completed (though in practice
     *         an exception is thrown on failure)
     * @throws RuntimeException if an unexpected error occurs while deleting
     *         associated data (e.g., database constraint violation)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(authService.delete(user.getUsuario()));
    }
}