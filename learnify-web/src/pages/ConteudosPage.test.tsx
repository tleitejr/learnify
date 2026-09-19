import userEvent from "@testing-library/user-event";
import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen, waitFor } from "../test/test-utils";
import ConteudosPage from "./ConteudosPage";

describe("ConteudosPage", () => {
  it("shows fallback when disciplina is missing", () => {
    renderWithProviders(<ConteudosPage />);
    expect(screen.getByText("Disciplina não informada")).toBeInTheDocument();
  });

  it("renders conteúdos from route params and navigates only for unconcluded item", async () => {
    const user = userEvent.setup();
    renderWithProviders(
      <Routes>
        <Route
          path="/disciplinas/:disciplinaId/conteudos"
          element={<ConteudosPage />}
        />
        <Route
          path="/conteudos/:conteudoId/quiz"
          element={<div>Quiz Route</div>}
        />
      </Routes>,
      { route: "/disciplinas/d-1/conteudos" },
    );

    expect(await screen.findByText("Função do 1º grau")).toBeInTheDocument();
    expect(screen.getByText("Equação do 2º grau")).toBeInTheDocument();

    await user.click(screen.getByText("Função do 1º grau"));
    await waitFor(() => {
      expect(screen.getByText("Quiz Route")).toBeInTheDocument();
    });
  });
});
