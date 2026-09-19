import { HttpResponse, http } from "msw";

export const profileHandlers = [
  http.get(/\/usuarios\/me$/, async () => {
    return HttpResponse.json({
      id: "u-1",
      nome: "Antonio",
      email: "antonio@learnify.dev",
      nivel: 3,
      pontuacaoTotal: 2450,
    });
  }),
  http.delete(/\/auth\/delete$/, async () => {
    return HttpResponse.json("Conta deletada");
  }),
];
