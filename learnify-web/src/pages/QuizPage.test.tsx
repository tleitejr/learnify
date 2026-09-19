import userEvent from "@testing-library/user-event";
import { HttpResponse, http } from "msw";
import { Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import { server } from "../test/mocks/server";
import { renderWithProviders, screen } from "../test/test-utils";
import QuizPage from "./QuizPage";

const toastSuccess = vi.fn();
const toastError = vi.fn();

vi.mock("sonner", () => ({
  toast: {
    success: (...args: unknown[]) => toastSuccess(...args),
    error: (...args: unknown[]) => toastError(...args),
  },
}));

describe("QuizPage", () => {
  beforeEach(() => {
    toastSuccess.mockClear();
    toastError.mockClear();
  });

  it("shows invalid activity when no route param is provided", () => {
    renderWithProviders(<QuizPage />);
    expect(screen.getByText("Atividade inválida.")).toBeInTheDocument();
  });

  it("shows warning, starts quiz and completes flow", async () => {
    const user = userEvent.setup();

    renderWithProviders(
      <Routes>
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      { route: "/conteudos/ct-1/quiz" },
    );

    expect(screen.getByText("Atenção")).toBeInTheDocument();
    await user.click(screen.getByRole("button", { name: "Iniciar" }));

    expect(await screen.findByText("Quanto é 2 + 2?")).toBeInTheDocument();
    await user.click(screen.getByRole("button", { name: "4" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );

    expect(await screen.findByText("Quanto é 3 + 1?")).toBeInTheDocument();
    await user.click(screen.getByRole("button", { name: "4" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );

    expect(await screen.findByText("Quiz Finalizado 🎉")).toBeInTheDocument();
    expect(screen.getByText("200 XP")).toBeInTheDocument();
    expect(toastSuccess).toHaveBeenCalled();
  });

  it("goes back when clicking Voltar on warning screen", async () => {
    const user = userEvent.setup();
    renderWithProviders(
      <Routes>
        <Route path="/anterior" element={<div>Página anterior</div>} />
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      {
        initialEntries: ["/anterior", "/conteudos/ct-1/quiz"],
        initialIndex: 1,
      },
    );

    await user.click(screen.getByRole("button", { name: "Voltar" }));
    expect(await screen.findByText("Página anterior")).toBeInTheDocument();
  });

  it("shows incorrect answer toast", async () => {
    const user = userEvent.setup();
    renderWithProviders(
      <Routes>
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      { route: "/conteudos/ct-1/quiz" },
    );

    await user.click(screen.getByRole("button", { name: "Iniciar" }));
    await user.click(await screen.findByRole("button", { name: "3" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );

    expect(toastError).toHaveBeenCalledWith("Resposta incorreta");
  });

  it("shows error and retry action when quiz fetch fails", async () => {
    const user = userEvent.setup();
    server.use(
      http.get(/\/conteudos\/[^/]+\/quiz$/, () => {
        return HttpResponse.json({ error: "fail" }, { status: 500 });
      }),
    );

    renderWithProviders(
      <Routes>
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      { route: "/conteudos/ct-1/quiz" },
    );

    await user.click(screen.getByRole("button", { name: "Iniciar" }));
    expect(
      await screen.findByText("Erro ao carregar quiz."),
    ).toBeInTheDocument();

    server.use(
      http.get(/\/conteudos\/[^/]+\/quiz$/, () => {
        return HttpResponse.json([
          {
            id: "q-1",
            enunciado: "Quanto é 2 + 2?",
            alternativas: [
              { id: "a-1", descricao: "3" },
              { id: "a-2", descricao: "4" },
            ],
          },
        ]);
      }),
    );

    await user.click(screen.getByRole("button", { name: "Tentar novamente" }));
    expect(await screen.findByText("Quanto é 2 + 2?")).toBeInTheDocument();
  });

  it("shows error toast when submit fails", async () => {
    const user = userEvent.setup();
    server.use(
      http.post(/\/quiz\/responder$/, () => {
        return HttpResponse.json({ error: "fail" }, { status: 500 });
      }),
    );

    renderWithProviders(
      <Routes>
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      { route: "/conteudos/ct-1/quiz" },
    );

    await user.click(screen.getByRole("button", { name: "Iniciar" }));
    await user.click(await screen.findByRole("button", { name: "4" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );

    expect(toastError).toHaveBeenCalledWith("Erro ao responder quiz");
  });

  it("navigates back from finished screen", async () => {
    const user = userEvent.setup();
    renderWithProviders(
      <Routes>
        <Route path="/anterior" element={<div>Página anterior</div>} />
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      {
        initialEntries: ["/anterior", "/conteudos/ct-1/quiz"],
        initialIndex: 1,
      },
    );

    await user.click(screen.getByRole("button", { name: "Iniciar" }));
    await user.click(await screen.findByRole("button", { name: "4" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );
    await user.click(await screen.findByRole("button", { name: "4" }));
    await user.click(
      screen.getByRole("button", { name: "Confirmar Resposta" }),
    );
    await user.click(await screen.findByRole("button", { name: "Continuar" }));

    expect(await screen.findByText("Página anterior")).toBeInTheDocument();
  });

  it("shows empty quiz fallback when API returns no questions", async () => {
    const user = userEvent.setup();
    server.use(
      http.get(/\/conteudos\/[^/]+\/quiz$/, () => {
        return HttpResponse.json([]);
      }),
    );

    renderWithProviders(
      <Routes>
        <Route path="/conteudos/:conteudoId/quiz" element={<QuizPage />} />
      </Routes>,
      { route: "/conteudos/ct-1/quiz" },
    );

    await user.click(screen.getByRole("button", { name: "Iniciar" }));
    expect(
      await screen.findByText("Nenhuma questão encontrada."),
    ).toBeInTheDocument();
  });
});
