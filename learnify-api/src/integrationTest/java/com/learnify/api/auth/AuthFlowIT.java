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

package com.learnify.api.auth;

import com.learnify.api.AbstractIT;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.repository.RegistroAcessoRepository;
import com.learnify.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthFlowIT extends AbstractIT {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroAcessoRepository registroAcessoRepository;

    @Test
    void signupLoginLogoutAndDeletePersistTheAccountLifecycle() throws Exception {
        String email = "ana.silva@example.com";
        String password = "SenhaSegura123";
        String token = signup("Ana Silva", email, password);

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        assertThat(usuario.getSenha()).isNotEqualTo(password);
        assertThat(registroAcessoRepository.findTopByUsuarioIdOrderByDataLoginDesc(usuario.getId())).isPresent();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\",\"senha\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(3600));

        mockMvc.perform(post("/api/v1/auth/logout").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout feito com sucesso!"));

        mockMvc.perform(delete("/api/v1/auth/delete").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        assertThat(usuarioRepository.findByEmail(email)).isEmpty();
        assertThat(registroAcessoRepository.count()).isZero();
    }

    @Test
    void authenticationErrorsReturnTheMappedApiError() throws Exception {
        signup("Bruno Lima", "bruno@example.com", "SenhaSegura123");

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Outro Bruno\",\"email\":\"bruno@example.com\",\"senha\":\"SenhaSegura123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email já cadastrado!"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"ausente@example.com\",\"senha\":\"SenhaSegura123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado!"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"bruno@example.com\",\"senha\":\"incorreta\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciais inválidas!"));

        mockMvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }
}