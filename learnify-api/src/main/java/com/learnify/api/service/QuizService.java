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

import com.learnify.api.domain.entity.*;
import com.learnify.api.dto.response.AlternativaResponseDTO;
import com.learnify.api.dto.response.QuestaoResponseDTO;
import com.learnify.api.dto.response.ResultadoQuizResponseDTO;
import com.learnify.api.exception.BusinessException;
import com.learnify.api.exception.ResourceNotFoundException;
import com.learnify.api.repository.*;
import com.learnify.api.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service responsible for quiz execution and answer processing.
 *
 * <p>Loads questions and alternatives for a content item, validates and records
 * user answers, awards points, updates the user's total score and level, updates
 * progress, checks achievements, and marks content as completed when all
 * questions have been answered.
 *
 * <p>Key Features:
 * <ul>
 *     <li>Load quiz questions and alternatives</li>
 *     <li>Process and validate answers</li>
 *     <li>Award points and update user level</li>
 *     <li>Update progress and unlock achievements</li>
 *     <li>Detect completed content</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);

    private final QuestaoRepository questaoRepository;
    private final ConteudoUsuarioRepository conteudoUsuarioRepository;
    private final AlternativaRepository alternativaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final ProgressoService progressoService;
    private final GamificacaoService gamificacaoService;
    private final RespostaUsuarioRepository respostaUsuarioRepository;

    public QuizService(
            QuestaoRepository questaoRepository,
            ConteudoUsuarioRepository conteudoUsuarioRepository,
            AlternativaRepository alternativaRepository,
            UsuarioRepository usuarioRepository,
            PontuacaoRepository pontuacaoRepository,
            ProgressoService progressoService,
            GamificacaoService gamificacaoService,
            RespostaUsuarioRepository respostaUsuarioRepository
    ) {
        this.questaoRepository = questaoRepository;
        this.conteudoUsuarioRepository = conteudoUsuarioRepository;
        this.alternativaRepository = alternativaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.progressoService = progressoService;
        this.gamificacaoService = gamificacaoService;
        this.respostaUsuarioRepository = respostaUsuarioRepository;
    }

    /**
     * Processes a user's answer to a quiz question.
     *
     * <p>Validates that the question has not been answered before, determines
     * correctness, awards points (10 if correct), updates the user's total score
     * and level, records the answer, updates progress, and checks for new achievements.
     * If all questions for a content are answered, the content is marked as completed.
     *
     * @param usuario       the authenticated user
     * @param questaoId     the ID of the question being answered
     * @param alternativaId the ID of the selected alternative
     * @return a response DTO containing correctness, points earned, updated total
     *         score, level, and optionally a newly unlocked achievement
     * @throws ResourceNotFoundException if the alternative or associated data is not found
     * @throws BusinessException if the question was already answered by the user
     */
    public ResultadoQuizResponseDTO responder(
        Usuario usuario,
        UUID questaoId,
        UUID alternativaId
    ) {
        String email = SecurityUtils.getEmailUsuarioLogado();

        log.info("Processando resposta... | Email do usuário: {} | ID da questão: {}", email, questaoId);

        var alternativa = alternativaRepository.findById(alternativaId)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada!"));

        var questao = alternativa.getQuestao();
        var conteudo = questao.getConteudo();

        boolean jaRespondeu = respostaUsuarioRepository
                .existsByUsuarioIdAndQuestaoId(usuario.getId(), questao.getId());

        if (jaRespondeu) {
            log.warn(
                    "Fraude detectada! | ID do usuário: {} | ID da questão: {}",
                    usuario.getId(),
                    questao.getId()
            );

            throw new BusinessException("Questão já respondida!");
        }

        boolean correta = alternativa.getCorreta();
        int pontos = correta ? 10 : 0;

        log.info("Resposta avaliada! | correta: {} | pontos: {}", correta, pontos);

        RespostaUsuario resposta = new RespostaUsuario();
        resposta.setUsuario(usuario);
        resposta.setQuestao(questao);
        resposta.setConteudo(conteudo);
        resposta.setAlternativaSelecionada(alternativa);
        resposta.setCorreta(correta);
        resposta.setPontosObtidos(pontos);
        resposta.setDataResposta(LocalDateTime.now());

        respostaUsuarioRepository.save(resposta);

        log.info(
                "Resposta registrada! | ID do usuário: {} | ID da questão: {} | correta: {}",
                usuario.getId(),
                questao.getId(),
                correta
        );

        if (correta) {
            Pontuacao pontuacao = new Pontuacao();
            pontuacao.setUsuario(usuario);
            pontuacao.setQuestao(questao);
            pontuacao.setConteudo(conteudo);
            pontuacao.setPontosObtidos(pontos);

            pontuacaoRepository.save(pontuacao);
        
            usuario.setPontuacaoTotal(usuario.getPontuacaoTotal() + pontos);

            int novoNivel = (usuario.getPontuacaoTotal() / 100) + 1;
            usuario.setNivel(novoNivel);

            usuarioRepository.save(usuario);

            log.info(
                    "Usuário atualizado com sucesso! | Pontuação Total: {} | Nível: {}",
                    usuario.getPontuacaoTotal(),
                    usuario.getNivel()
            );
        }

        Optional<String> conquista = gamificacaoService.verificarConquistas(usuario);

        conquista.ifPresent(c ->
                log.info(
                        "Conquista desbloqueada! | ID do usuário: {} | Conquista: {}",
                        usuario.getId(),
                        c
                )
        );

        progressoService.atualizarProgresso(
                usuario.getId(),
                conteudo.getId(),
                pontos
        );

        verifyConteudoConcluido(usuario, conteudo);

        return new ResultadoQuizResponseDTO(
                correta,
                pontos,
                usuario.getPontuacaoTotal(),
                usuario.getNivel(),
                conquista.orElse(null)
        );
    }

    /**
     * Loads all questions for a given content item, including their alternatives.
     *
     * <p>Returns a list of question DTOs, each containing the question ID,
     * statement, number, and a list of alternative DTOs (with ID and description).
     *
     * @param conteudoId the ID of the content
     * @return a list of {@link QuestaoResponseDTO} representing the quiz
     */
    public List<QuestaoResponseDTO> carregarQuiz(UUID conteudoId) {
        log.info("Carregando quiz... | ID do conteúdo: {}", conteudoId);

        var questoes = questaoRepository.findByConteudoId(conteudoId);

        log.info("Quantidade de questões encontradas: {}", questoes.size());

        return questoes
                .stream()
                .map(
                        questao -> new QuestaoResponseDTO(
                                questao.getId(),
                                questao.getEnunciado(),
                                questao.getNumero(),
                                alternativaRepository
                                        .findByQuestaoId(questao.getId())
                                        .stream()
                                        .map(
                                                alternativa -> new AlternativaResponseDTO(
                                                        alternativa.getId(),
                                                        alternativa.getDescricao()
                                        ))
                                        .toList()
                        ))
                .toList();
    }

    /**
     * Verifies if all questions of a content have been answered and marks it as completed.
     *
     * <p>If the number of answered questions equals the total number of questions
     * for the content, the associated ConteudoUsuario record is updated to set
     * `concluido = true`.
     *
     * @param usuario  the user entity
     * @param conteudo the content entity
     * @throws IllegalStateException if the ConteudoUsuario association is not found
     */
    private void verifyConteudoConcluido(Usuario usuario, Conteudo conteudo) {
        log.info(
                "Verificando conteúdo finalizado... | ID do usuario: {} | ID do conteúdo: {}",
                usuario.getId(), conteudo.getId()
        );
        var questoes = questaoRepository.findByConteudoId(conteudo.getId()).stream().toList();
        List<RespostaUsuario> respostas = respostaUsuarioRepository
                .findByUsuarioIdAndConteudoId(usuario.getId(), conteudo.getId()).stream().toList();

        if (questoes.size() == respostas.size()) {
            log.info("Conteúdo finalizado! | ID do conteúdo: {}", conteudo.getId());
            ConteudoUsuario conteudoUsuario = conteudoUsuarioRepository
                    .findByUsuarioAndConteudo(usuario, conteudo)
                    .orElseThrow(
                            () -> new IllegalStateException(
                                "Associação não encontrada para o usuário e conteúdo"
                            )
                    );

            conteudoUsuario.setConcluido(true);
            conteudoUsuarioRepository.save(conteudoUsuario);
        }
    }
}