import { HttpResponse, http } from "msw";

export const dashboardHandlers = [
  http.get(/\/usuarios\/me\/estatisticas$/, async () => {
    return HttpResponse.json({
      totalRespostas: 10,
      acertos: 7,
      erros: 3,
      percentual: 70,
      pontuacaoTotal: 2450,
      mediaPontos: 120,
      nivel: 3,
      desempenho: "Bom",
    });
  }),
  http.get(/\/usuarios\/me\/conquistas$/, async () => {
    return HttpResponse.json([
      {
        id: "c-1",
        titulo: "Primeiros passos",
        descricao: "Completou a primeira atividade",
      },
      {
        id: "c-2",
        titulo: "Em ritmo forte",
        descricao: "7 acertos",
      },
    ]);
  }),
];
