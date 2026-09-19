import { HttpResponse, http } from "msw";

export const quizHandlers = [
  http.get(/\/conteudos\/[^/]+\/quiz$/, async () => {
    return HttpResponse.json([
      {
        id: "q-1",
        enunciado: "Quanto é 2 + 2?",
        alternativas: [
          { id: "a-1", descricao: "3" },
          { id: "a-2", descricao: "4" },
        ],
      },
      {
        id: "q-2",
        enunciado: "Quanto é 3 + 1?",
        alternativas: [
          { id: "a-3", descricao: "4" },
          { id: "a-4", descricao: "5" },
        ],
      },
    ]);
  }),
  http.post(/\/quiz\/responder$/, async ({ request }) => {
    const body = (await request.json()) as { alternativaSelecionadaId: string };
    const correta =
      body.alternativaSelecionadaId === "a-2" ||
      body.alternativaSelecionadaId === "a-3";

    return HttpResponse.json({
      correta,
      pontosGanhos: correta ? 100 : 0,
      pontuacaoTotal: correta ? 2550 : 2450,
      nivel: 3,
      conquistaDesbloqueada: correta ? "Acertador" : undefined,
    });
  }),
];
