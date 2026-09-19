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

import com.learnify.api.domain.entity.Alternativa;
import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.domain.entity.Disciplina;
import com.learnify.api.domain.entity.Questao;
import com.learnify.api.domain.entity.RespostaUsuario;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.PapelUsuario;
import com.learnify.api.domain.enums.TipoDisciplina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class RespostaUsuarioRepositoryTest {

    @Autowired
    private RespostaUsuarioRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ConteudoRepository conteudoRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private AlternativaRepository alternativaRepository;

    private Usuario usuario;
    private Conteudo conteudo;
    private Questao questao;
    private Alternativa alternativa;

    @BeforeEach
    void setUp() {
        
        usuario = usuarioRepository.save(Usuario.builder().nome("User").email(UUID.randomUUID() + "@repo.test")
                .senha("hash").papel(PapelUsuario.ESTUDANTE).build());
        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder().tipo(TipoDisciplina.HISTORIA).build());
        conteudo = conteudoRepository.save(Conteudo.builder().titulo(UUID.randomUUID().toString()).disciplina(disciplina).build());
        questao = questaoRepository.save(Questao.builder().enunciado(UUID.randomUUID().toString()).numero(1).conteudo(conteudo).build());
        alternativa = alternativaRepository.save(Alternativa.builder().descricao(UUID.randomUUID().toString())
                .correta(true).questao(questao).build());
    }

    @Test
    void shouldCountAnswersAndCalculateAveragePoints() {
        
        respostaRepository.save(new RespostaUsuario(null, usuario, questao, conteudo, true, alternativa, 10, null));
        respostaRepository.save(new RespostaUsuario(null, usuario, questao, conteudo, false, alternativa, 0, null));

        
        long total = respostaRepository.countByUsuarioId(usuario.getId());
        long correct = respostaRepository.countByUsuarioIdAndCorretaTrue(usuario.getId());
        long incorrect = respostaRepository.countByUsuarioIdAndCorretaFalse(usuario.getId());
        Double average = respostaRepository.mediaPontos(usuario.getId());

        
        assertEquals(2, total);
        assertEquals(1, correct);
        assertEquals(1, incorrect);
        assertEquals(5.0, average);
    }

    @Test
    void shouldFindAnswerByUserAndContentAndDetectDuplicates() {
        
        RespostaUsuario answer = respostaRepository.save(new RespostaUsuario(null, usuario, questao, conteudo, true, alternativa, 10, null));

        
        var result = respostaRepository.findByUsuarioIdAndConteudoId(usuario.getId(), conteudo.getId());
        boolean exists = respostaRepository.existsByUsuarioIdAndQuestaoId(usuario.getId(), questao.getId());
        boolean missing = respostaRepository.existsByUsuarioIdAndQuestaoId(UUID.randomUUID(), questao.getId());

        
        assertEquals(1, result.size());
        assertEquals(answer.getId(), result.get(0).getId());
        assertTrue(exists);
        assertFalse(missing);
    }
}