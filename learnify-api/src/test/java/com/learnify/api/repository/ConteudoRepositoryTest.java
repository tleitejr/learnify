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

import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.domain.entity.ConteudoUsuario;
import com.learnify.api.domain.entity.Disciplina;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.domain.enums.TipoDisciplina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ConteudoRepositoryTest {

    @Autowired
    private ConteudoRepository conteudoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConteudoUsuarioRepository conteudoUsuarioRepository;

    @Test
    void shouldReturnContentWithCompletionStatusForUser() {
        
        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder().tipo(TipoDisciplina.MATEMATICA).build());
        Usuario usuario = usuarioRepository.save(Usuario.builder().nome("Ana").email("ana@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        Conteudo completed = conteudoRepository.save(Conteudo.builder().titulo("Algebra").disciplina(disciplina).build());
        Conteudo notCompleted = conteudoRepository.save(Conteudo.builder().titulo("Geometria").disciplina(disciplina).build());
        conteudoUsuarioRepository.save(ConteudoUsuario.builder().usuario(usuario).conteudo(completed).concluido(true).build());

        
        var result = conteudoRepository.findConteudosByDisciplinaAndUsuario(disciplina.getId(), usuario.getId());

        
        assertEquals(2, result.size());
        assertTrue(result.stream().filter(item -> item.id().equals(completed.getId())).findFirst().orElseThrow().concluido());
        assertFalse(result.stream().filter(item -> item.id().equals(notCompleted.getId())).findFirst().orElseThrow().concluido());
    }

    @Test
    void shouldReturnEmptyWhenDisciplineHasNoContent() {
        
        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder().tipo(TipoDisciplina.PORTUGUES).build());

        
        List<?> result = conteudoRepository.findConteudosByDisciplinaAndUsuario(disciplina.getId(), UUID.randomUUID());

        
        assertTrue(result.isEmpty());
    }
}