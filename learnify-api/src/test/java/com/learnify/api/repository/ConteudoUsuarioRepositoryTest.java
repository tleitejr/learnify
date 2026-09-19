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
import com.learnify.api.domain.entity.Progresso;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.domain.enums.TipoDisciplina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ConteudoUsuarioRepositoryTest {

    @Autowired
    private ConteudoUsuarioRepository conteudoUsuarioRepository;

    @Autowired
    private ProgressoRepository progressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ConteudoRepository conteudoRepository;

    @Test
        void shouldExposePostgresSpecificFunctionWhenRunAgainstH2() {
        
        Usuario usuario = usuarioRepository.save(Usuario.builder().nome("User").email("bulk@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder().tipo(TipoDisciplina.BIOLOGIA).build());
        conteudoRepository.save(Conteudo.builder().titulo("Content A").disciplina(disciplina).build());
        conteudoRepository.save(Conteudo.builder().titulo("Content B").disciplina(disciplina).build());

        
        InvalidDataAccessResourceUsageException exception = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidDataAccessResourceUsageException.class,
                () -> conteudoUsuarioRepository.inserirConteudosFaltantes(usuario.getId())
        );

        
        assertTrue(exception.getMostSpecificCause().getMessage().contains("GEN_RANDOM_UUID"));
    }

    @Test
    void shouldFindAssociationAndProgressForUserAndContent() {
        
        Usuario usuario = usuarioRepository.save(Usuario.builder().nome("User").email("association@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder().tipo(TipoDisciplina.QUIMICA).build());
        Conteudo conteudo = conteudoRepository.save(Conteudo.builder().titulo("Content").disciplina(disciplina).build());
        ConteudoUsuario association = conteudoUsuarioRepository.save(ConteudoUsuario.builder()
                .usuario(usuario).conteudo(conteudo).concluido(false).build());
        Progresso progresso = progressoRepository.save(Progresso.builder().usuario(usuario).conteudo(conteudo)
                .percentual(0.5).concluido(false).build());

        
        var foundAssociation = conteudoUsuarioRepository.findByUsuarioAndConteudo(usuario, conteudo);
        var foundProgress = progressoRepository.findByUsuarioIdAndConteudoId(usuario.getId(), conteudo.getId());

        
        assertEquals(association.getId(), foundAssociation.orElseThrow().getId());
        assertEquals(progresso.getId(), foundProgress.orElseThrow().getId());
        assertFalse(foundProgress.orElseThrow().getConcluido());
    }
}