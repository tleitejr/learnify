import { HttpResponse, http } from "msw";

export const contentsHandlers = [
  http.get(/\/disciplinas\/[^/]+\/conteudos$/, async () => {
    return HttpResponse.json([
      {
        id: "ct-1",
        titulo: "Função do 1º grau",
        concluido: false,
        ativo: true,
        dataCriacao: "2026-01-01T00:00:00.000Z",
      },
      {
        id: "ct-2",
        titulo: "Equação do 2º grau",
        concluido: true,
        ativo: true,
        dataCriacao: "2026-01-01T00:00:00.000Z",
      },
    ]);
  }),
];
