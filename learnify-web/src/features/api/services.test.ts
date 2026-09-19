import { HttpResponse, http } from "msw";
import { describe, expect, it } from "vitest";
import { loginRequest } from "../auth/api/login";
import { logoutRequest } from "../auth/api/logout";
import { signupRequest } from "../auth/api/signup";
import { getConteudos } from "../contents/api/getConteudos";
import { getConquistas } from "../dashboard/api/getConquistas";
import { getEstatisticas } from "../dashboard/api/getEstatisticas";
import { getDisciplinas } from "../disciplina/api/getDisciplinas";
import { excluirConta } from "../perfil/api/excluirConta";
import { getMe } from "../perfil/api/getMe";
import { getQuiz } from "../quiz/api/getQuiz";
import { responderQuiz } from "../quiz/api/responderQuiz";
import { getRanking } from "../ranking/api/getRanking";
import { server } from "../../test/mocks/server";

describe("feature API services", () => {
  it("handles auth services", async () => {
    await expect(loginRequest("a@b.com", "123")).resolves.toEqual({
      token: "token-123",
    });
    await expect(signupRequest("Antonio", "a@b.com", "123")).resolves.toEqual({
      token: "token-123",
    });
    await expect(logoutRequest()).resolves.toEqual({
      message: "Logout realizado com sucesso.",
      dataLogout: "2026-01-01T00:00:00.000Z",
    });
  });

  it("handles profile services", async () => {
    await expect(getMe()).resolves.toMatchObject({
      id: "u-1",
      nome: "Antonio",
    });
    await expect(excluirConta()).resolves.toBe("Conta deletada");
  });

  it("handles dashboard and ranking services", async () => {
    await expect(getEstatisticas()).resolves.toMatchObject({
      acertos: 7,
      erros: 3,
    });
    await expect(getConquistas()).resolves.toHaveLength(2);
    await expect(getRanking()).resolves.toMatchObject({
      content: expect.any(Array),
    });
  });

  it("handles disciplinas, conteúdos and quiz services", async () => {
    await expect(getDisciplinas()).resolves.toHaveLength(2);
    await expect(getConteudos("d-1")).resolves.toHaveLength(2);
    await expect(getQuiz("ct-1")).resolves.toHaveLength(2);
    await expect(
      responderQuiz({ questaoId: "q-1", alternativaSelecionadaId: "a-2" }),
    ).resolves.toMatchObject({
      correta: true,
      pontosGanhos: 100,
    });
  });

  it("propagates service errors", async () => {
    server.use(
      http.post(/\/auth\/login$/, () => {
        return HttpResponse.json({ error: "fail" }, { status: 500 });
      }),
    );

    await expect(loginRequest("a@b.com", "123")).rejects.toBeTruthy();
  });
});
