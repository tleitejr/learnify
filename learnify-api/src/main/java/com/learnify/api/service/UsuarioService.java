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

import com.learnify.api.domain.entity.Usuario;
import com.learnify.api.domain.enums.TipoDesempenho;
import com.learnify.api.dto.response.ConquistaResponseDTO;
import com.learnify.api.dto.response.EstatisticasUsuarioResponseDTO;
import com.learnify.api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for user profile, statistics, achievements, and deletion.
 *
 * <p>Lists achievements earned by a user, computes detailed quiz performance
 * statistics (correct/incorrect answers, success percentage, average points,
 * total score, level, and performance classification), and deletes a user
 * together with all related records.
 *
 * <p>Key Features:
 * <ul>
 *     <li>List user achievements</li>
 *     <li>Calculate user quiz statistics and performance</li>
 *     <li>Delete user and associated data</li>
 * </ul>
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioConquistaRepository usuarioConquistaRepository;
    private final RespostaUsuarioRepository respostaUsuarioRepository;
    private final ConteudoUsuarioRepository conteudoUsuarioRepository;
    private final RegistroAcessoRepository registroAcessoRepository;
    private final PontuacaoRepository pontuacaoRepository;
    private final ProgressoRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
            UsuarioConquistaRepository usuarioConquistaRepository,
            RespostaUsuarioRepository respostaUsuarioRepository,
            ConteudoUsuarioRepository conteudoUsuarioRepository,
            RegistroAcessoRepository registroAcessoRepository,
            PontuacaoRepository pontuacaoRepository,
            ProgressoRepository progressoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.usuarioConquistaRepository = usuarioConquistaRepository;
        this.respostaUsuarioRepository = respostaUsuarioRepository;
        this.conteudoUsuarioRepository = conteudoUsuarioRepository;
        this.registroAcessoRepository = registroAcessoRepository;
        this.pontuacaoRepository = pontuacaoRepository;
        this.progressoRepository = progressoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lists all achievements earned by a user.
     *
     * <p>Returns a list of achievement DTOs containing the achievement ID,
     * title, and description.
     *
     * @param usuarioId the ID of the user
     * @return a list of {@link ConquistaResponseDTO}
     */
    public List<ConquistaResponseDTO> listarConquistas(UUID usuarioId) {
        log.info("Buscando conquistas... | ID do usuário: {}", usuarioId);

        var usuarios = usuarioConquistaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(uc -> new ConquistaResponseDTO(
                        uc.getConquista().getId(),
                        uc.getConquista().getTitulo(),
                        uc.getConquista().getDescricao()
                ))
                .toList();

        log.info("Total de conquistas encontradas: {} | ID do usuário: {}", usuarios.size(), usuarioId);

        return usuarios;
    }

    private String classificarDesempenho(BigDecimal percentual) {
        if (percentual.compareTo(new BigDecimal("80")) >= 0) return TipoDesempenho.EXCELENTE.getMessage();
        if (percentual.compareTo(new BigDecimal("75")) >= 0) return TipoDesempenho.MUITO_BOM.getMessage();
        if (percentual.compareTo(new BigDecimal("60")) >= 0) return TipoDesempenho.BOM.getMessage();
        if (percentual.compareTo(new BigDecimal("50")) >= 0) return TipoDesempenho.REGULAR.getMessage();
        if (percentual.compareTo(new BigDecimal("30")) >= 0) return TipoDesempenho.RUIM.getMessage();
        if (percentual.compareTo(new BigDecimal("1")) >= 0) return TipoDesempenho.MUITO_RUIM.getMessage();
        return TipoDesempenho.REGULAR.getMessage();
    }

    /**
     * Computes detailed statistics for a user based on their quiz answers.
     *
     * <p>Calculates total questions answered, correct/incorrect counts,
     * success percentage, average points per answer, total score, level,
     * and a performance classification (e.g., EXCELENTE, BOM, etc.).
     *
     * @param usuario the user entity
     * @return a {@link EstatisticasUsuarioResponseDTO} containing all computed stats
     */
    public EstatisticasUsuarioResponseDTO obterEstatisticas(Usuario usuario) {
        UUID usuarioId = usuario.getId();

        log.info("Obtendo estatísticas de {} | ID: {}", usuario.getNome(), usuarioId);

        long total = respostaUsuarioRepository.countByUsuarioId(usuarioId);
        long acertos = respostaUsuarioRepository.countByUsuarioIdAndCorretaTrue(usuarioId);
        long erros = respostaUsuarioRepository.countByUsuarioIdAndCorretaFalse(usuarioId);

        BigDecimal percentual = BigDecimal.ZERO;
        if (total > 0) {
            BigDecimal acertosBD = BigDecimal.valueOf(acertos);
            BigDecimal totalBD = BigDecimal.valueOf(total);
            BigDecimal cem = new BigDecimal("100");

            percentual = acertosBD.multiply(cem).divide(totalBD, 2, RoundingMode.HALF_UP);
        }

        Double media = respostaUsuarioRepository.mediaPontos(usuarioId);
        BigDecimal mediaPontos = new BigDecimal(media).setScale(1, RoundingMode.HALF_UP);

        int pontuacaoTotal = usuario.getPontuacaoTotal();
        int nivel = usuario.getNivel();

        String desempenho = classificarDesempenho(percentual);

        log.info("Estatísticas calculadas! | total: {} | acertos: {} | erros: {} | %: {}",
                total, acertos, erros, percentual
        );

        log.info("Resumo desempenho! | ID do usuário: {} | percentual: {} | nível: {} | desempenho: {}",
                usuarioId, percentual, nivel, desempenho
        );

        return new EstatisticasUsuarioResponseDTO(
                total,
                acertos,
                erros,
                percentual,
                pontuacaoTotal,
                mediaPontos,
                nivel,
                desempenho
        );
    }

    /**
     * Deletes a user and all associated records.
     *
     * <p>First deletes related entities (conquests, answers, content associations,
     * access logs, scores, progress) via {@link #deleteRepositoryByUsuario(Usuario)},
     * then removes the user entity itself.
     *
     * @param usuario the user to delete
     * @return {@code true} if the deletion was successful, {@code false} otherwise
     * @throws RuntimeException if an error occurs while deleting associated data
     */
    public boolean delete(Usuario usuario) {
        if (deleteRepositoryByUsuario(usuario)) {
            usuarioRepository.delete(usuario);
            return true;
        } else {
            return false;
        }
    }

    private boolean deleteRepositoryByUsuario(Usuario usuario) {
        try {
            usuarioConquistaRepository.deleteByUsuarioId(usuario.getId());
            respostaUsuarioRepository.deleteByUsuarioId(usuario.getId());
            conteudoUsuarioRepository.deleteByUsuarioId(usuario.getId());
            registroAcessoRepository.deleteByUsuarioId(usuario.getId());
            pontuacaoRepository.deleteByUsuarioId(usuario.getId());
            progressoRepository.deleteByUsuarioId(usuario.getId());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}