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

package com.learnify.api.repository;

import com.learnify.api.domain.entity.Conquista;
import com.learnify.api.domain.entity.RegistroAcesso;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.entity.UsuarioConquista;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.domain.enums.TipoConquista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class RepositoryQueryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Autowired
    private RegistroAcessoRepository registroAcessoRepository;

    @Test
    void shouldFindUsersByEmailAndOrderRankingByScore() {
        
        Usuario lower = usuarioRepository.save(Usuario.builder().nome("Lower").email("lower@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).pontuacaoTotal(10).build());
        Usuario higher = usuarioRepository.save(Usuario.builder().nome("Higher").email("higher@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).pontuacaoTotal(50).build());

        
        var found = usuarioRepository.findByEmail(higher.getEmail());
        var page = usuarioRepository.findAllByOrderByPontuacaoTotalDesc(PageRequest.of(0, 10));

        
        assertEquals(higher.getId(), found.orElseThrow().getId());
        assertEquals(higher.getId(), page.getContent().get(0).getId());
        assertEquals(lower.getId(), page.getContent().get(1).getId());
    }

    @Test
    void shouldFindAchievementAndUserAssociations() {
        
        Usuario usuario = usuarioRepository.save(Usuario.builder().nome("User").email("achievement@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        Conquista conquista = conquistaRepository.save(Conquista.builder().codigo("APRENDIZ").titulo("Aprendiz")
                .descricao("First points").tipo(TipoConquista.PONTOS).valorCriterio(10).build());
        UsuarioConquista association = usuarioConquistaRepository.save(UsuarioConquista.builder().usuario(usuario).conquista(conquista).build());

        
        var associations = usuarioConquistaRepository.findByUsuarioId(usuario.getId());
        boolean exists = usuarioConquistaRepository.existsByUsuarioIdAndConquistaId(usuario.getId(), conquista.getId());
        var found = conquistaRepository.findByCodigo("APRENDIZ");

        
        assertEquals(association.getId(), associations.get(0).getId());
        assertTrue(exists);
        assertEquals(conquista.getId(), found.orElseThrow().getId());
    }

    @Test
    void shouldReturnLatestAccessLog() {
        
        Usuario usuario = usuarioRepository.save(Usuario.builder().nome("Access User").email("access@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        registroAcessoRepository.save(RegistroAcesso.builder().usuario(usuario)
                .dataLogin(LocalDateTime.now().minusHours(2)).build());
        RegistroAcesso latest = registroAcessoRepository.save(RegistroAcesso.builder().usuario(usuario)
                .dataLogin(LocalDateTime.now()).build());

        
        var result = registroAcessoRepository.findTopByUsuarioIdOrderByDataLoginDesc(usuario.getId());

        
        assertEquals(latest.getId(), result.orElseThrow().getId());
    }
}