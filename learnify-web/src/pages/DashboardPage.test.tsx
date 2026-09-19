import { HttpResponse, delay, http } from "msw";
import { describe, expect, it } from "vitest";
import { server } from "../test/mocks/server";
import { renderWithProviders, screen } from "../test/test-utils";
import DashboardPage from "./DashboardPage";

describe("DashboardPage", () => {
  it("renders loading and then dashboard data", async () => {
    server.use(
      http.get(/\/usuarios\/me$/, async () => {
        await delay(100);
        return HttpResponse.json({
          id: "u-1",
          nome: "Antonio",
          email: "antonio@learnify.dev",
          nivel: 3,
          pontuacaoTotal: 2450,
        });
      }),
    );

    renderWithProviders(<DashboardPage />);

    expect(screen.getByText("Desempenho")).toBeInTheDocument();
    expect(await screen.findByText("Olá, Antonio")).toBeInTheDocument();
    expect(
      screen.getByText("Faltam 550 XP para o próximo nível"),
    ).toBeInTheDocument();
    expect(screen.getByText("Acertos: 7")).toBeInTheDocument();
    expect(screen.getByText("Ana")).toBeInTheDocument();
  });

  it("renders empty achievements fallback", async () => {
    server.use(
      http.get(/\/usuarios\/me\/conquistas$/, () => {
        return HttpResponse.json([]);
      }),
    );

    renderWithProviders(<DashboardPage />);
    expect(
      await screen.findByText("Nenhuma conquista ainda."),
    ).toBeInTheDocument();
  });
});
