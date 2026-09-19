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

package com.learnify.api.quiz;

import com.learnify.api.AbstractIT;
import com.learnify.api.domain.entity.Alternativa;
import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.domain.entity.Disciplina;
import com.learnify.api.domain.entity.Progresso;
import com.learnify.api.domain.entity.Questao;
import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.TipoDisciplina;
import com.learnify.api.repository.AlternativaRepository;
import com.learnify.api.repository.ConquistaRepository;
import com.learnify.api.repository.ConteudoRepository;
import com.learnify.api.repository.ConteudoUsuarioRepository;
import com.learnify.api.repository.DisciplinaRepository;
import com.learnify.api.repository.ProgressoRepository;
import com.learnify.api.repository.QuestaoRepository;
import com.learnify.api.repository.RespostaUsuarioRepository;
import com.learnify.api.repository.UsuarioConquistaRepository;
import com.learnify.api.repository.UsuarioRepository;
import com.learnify.api.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuizWorkflowIT extends AbstractIT {

    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private ConteudoRepository conteudoRepository;
    @Autowired private QuestaoRepository questaoRepository;
    @Autowired private AlternativaRepository alternativaRepository;
    @Autowired private ConquistaRepository conquistaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ConteudoUsuarioRepository conteudoUsuarioRepository;
    @Autowired private ProgressoRepository progressoRepository;
    @Autowired private RespostaUsuarioRepository respostaUsuarioRepository;
    @Autowired private UsuarioConquistaRepository usuarioConquistaRepository;

    @Test
    void learnerCanProgressThroughContentAnswerQuizAndSeeGamificationResults() throws Exception {
        Disciplina disciplina = disciplinaRepository.save(TestDataFactory.disciplina(TipoDisciplina.MATEMATICA));
        Conteudo conteudo = conteudoRepository.save(TestDataFactory.conteudo(disciplina, "Razoes e proporcoes"));
        Questao questao = questaoRepository.save(TestDataFactory.questao(conteudo, 1));
        Alternativa correta = alternativaRepository.save(TestDataFactory.alternativa(questao, true));
        alternativaRepository.save(TestDataFactory.alternativa(questao, false));
        conquistaRepository.save(TestDataFactory.conquista("APRENDIZ", 10));

        String email = "carla.estudante@example.com";
        String token = signup("Carla Estudante", email, "SenhaSegura123");
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        progressoRepository.save(Progresso.builder().usuario(usuario).conteudo(conteudo).build());

        mockMvc.perform(get("/api/v1/disciplinas"))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/disciplinas").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").exists());
        mockMvc.perform(get("/api/v1/disciplinas/{id}/conteudos", disciplina.getId()))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/disciplinas/{id}/conteudos", disciplina.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Razoes e proporcoes"));
        mockMvc.perform(get("/api/v1/conteudos/{id}/quiz", conteudo.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(questao.getId().toString()))
                .andExpect(jsonPath("$[0].alternativas.length()").value(2));

        mockMvc.perform(get("/api/v1/usuarios/me").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
        assertThat(conteudoUsuarioRepository.findByUsuarioAndConteudo(usuario, conteudo)).isPresent();

        String resposta = "{\"questaoId\":\"" + questao.getId()
                + "\",\"alternativaSelecionadaId\":\"" + correta.getId() + "\"}";
        mockMvc.perform(post("/api/v1/quiz/responder")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resposta))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correta").value(true))
                .andExpect(jsonPath("$.pontosGanhos").value(10))
                .andExpect(jsonPath("$.pontuacaoTotal").value(10))
                .andExpect(jsonPath("$.conquistaDesbloqueada").value("Aprendiz"));

        mockMvc.perform(post("/api/v1/quiz/responder")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resposta))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Questão já respondida!"));

        mockMvc.perform(get("/api/v1/usuarios/me/estatisticas").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRespostas").value(1))
                .andExpect(jsonPath("$.acertos").value(1));
        mockMvc.perform(get("/api/v1/usuarios/me/conquistas").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Aprendiz"));
        mockMvc.perform(get("/api/v1/ranking?page=0&size=10").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nomeUsuario").value("Carla Estudante"));

        assertThat(respostaUsuarioRepository.countByUsuarioId(usuario.getId())).isEqualTo(1);
        Progresso progresso = progressoRepository
                .findByUsuarioIdAndConteudoId(usuario.getId(), conteudo.getId())
                .orElseThrow();
        assertThat(progresso.getConcluido()).isTrue();
        assertThat(progresso.getPercentual()).isEqualTo(1.1d);
        assertThat(conteudoUsuarioRepository.findByUsuarioAndConteudo(usuario, conteudo).orElseThrow().getConcluido()).isTrue();
        assertThat(usuarioConquistaRepository.findByUsuarioId(usuario.getId())).hasSize(1);
    }
}