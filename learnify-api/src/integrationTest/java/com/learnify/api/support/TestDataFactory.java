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

package com.learnify.api.support;

import com.learnify.api.domain.entity.Alternativa;
import com.learnify.api.domain.entity.Conquista;
import com.learnify.api.domain.entity.Conteudo;
import com.learnify.api.domain.entity.Disciplina;
import com.learnify.api.domain.entity.Questao;
import com.learnify.api.domain.enums.TipoConquista;
import com.learnify.api.domain.enums.TipoDisciplina;

import java.util.UUID;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static Disciplina disciplina(TipoDisciplina tipo) {
        return Disciplina.builder().tipo(tipo).ativo(true).build();
    }

    public static Conteudo conteudo(Disciplina disciplina, String titulo) {
        return Conteudo.builder().disciplina(disciplina).titulo(titulo).ativo(true).build();
    }

    public static Questao questao(Conteudo conteudo, int numero) {
        return Questao.builder()
                .conteudo(conteudo)
                .numero(numero)
                .enunciado("Pergunta " + numero + " " + UUID.randomUUID())
                .build();
    }

    public static Alternativa alternativa(Questao questao, boolean correta) {
        return Alternativa.builder()
                .questao(questao)
                .correta(correta)
                .descricao("Alternativa " + UUID.randomUUID())
                .build();
    }

    public static Conquista conquista(String codigo, int criterio) {
        return Conquista.builder()
                .codigo(codigo)
                .titulo(codigo.equals("APRENDIZ") ? "Aprendiz" : "Especialista")
                .descricao("Conquista " + codigo + " " + UUID.randomUUID())
                .tipo(TipoConquista.PONTOS)
                .valorCriterio(criterio)
                .build();
    }
}