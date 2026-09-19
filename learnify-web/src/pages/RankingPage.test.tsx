import { HttpResponse, delay, http } from "msw";
import { describe, expect, it } from "vitest";
import { server } from "../test/mocks/server";
import { renderWithProviders, screen } from "../test/test-utils";
import RankingPage from "./RankingPage";

describe("RankingPage", () => {
  it("shows loading state", async () => {
    server.use(
      http.get(/\/ranking$/, async () => {
        await delay(100);
        return HttpResponse.json({
          content: [],
        });
      }),
    );

    renderWithProviders(<RankingPage />);
    expect(screen.getByText("Carregando ranking...")).toBeInTheDocument();
    expect(await screen.findByText("Leaderboard")).toBeInTheDocument();
  });

  it("renders ranking data", async () => {
    renderWithProviders(<RankingPage />);

    expect(
      await screen.findByText("Alunos em destaque 🏆"),
    ).toBeInTheDocument();
    expect(screen.getByText("Ana")).toBeInTheDocument();
    expect(screen.getByText("Dani")).toBeInTheDocument();
    expect(screen.getByText("5000 XP")).toBeInTheDocument();
  });

  it("renders page with empty fallback arrays when request fails", async () => {
    server.use(
      http.get(/\/ranking$/, () => {
        return HttpResponse.json({ error: "fail" }, { status: 500 });
      }),
    );

    renderWithProviders(<RankingPage />);
    expect(await screen.findByText("Leaderboard")).toBeInTheDocument();
    expect(screen.queryByText("Ana")).not.toBeInTheDocument();
  });
});
