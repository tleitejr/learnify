import userEvent from "@testing-library/user-event";
import { HttpResponse, delay, http } from "msw";
import { Route, Routes } from "react-router-dom";
import { describe, expect, it, vi } from "vitest";
import { useAuthStore } from "../features/auth/authStore";
import { server } from "../test/mocks/server";
import { renderWithProviders, screen, waitFor } from "../test/test-utils";
import PerfilPage from "./PerfilPage";

const toastError = vi.fn();

vi.mock("sonner", () => ({
  toast: {
    error: (...args: unknown[]) => toastError(...args),
  },
}));

describe("PerfilPage", () => {
  it("renders user profile data and stats", async () => {
    renderWithProviders(<PerfilPage />);

    expect(await screen.findByText("Antonio")).toBeInTheDocument();
    expect(screen.getByText("antonio@learnify.dev")).toBeInTheDocument();
    expect(screen.getByText("Primeiros passos")).toBeInTheDocument();
    expect(screen.getByText("Bom")).toBeInTheDocument();
  });

  it("shows loading state for achievements section", async () => {
    server.use(
      http.get(/\/usuarios\/me\/conquistas$/, async () => {
        await delay(100);
        return HttpResponse.json([]);
      }),
    );

    renderWithProviders(<PerfilPage />);
    expect(await screen.findByText(/carregando/i)).toBeInTheDocument();
  });

  it("deletes account and redirects to login", async () => {
    const user = userEvent.setup();
    useAuthStore.setState({
      token: "token-1",
      user: {
        id: 1,
        nome: "A",
        email: "a@a.com",
        nivel: 1,
        pontuacaoTotal: 10,
      },
    });

    renderWithProviders(
      <Routes>
        <Route path="/perfil" element={<PerfilPage />} />
        <Route path="/login" element={<div>Login Route</div>} />
      </Routes>,
      { route: "/perfil" },
    );

    await screen.findByText("Antonio");
    await user.click(screen.getByRole("button", { name: "Deletar conta" }));
    await user.click(screen.getByRole("button", { name: "Deletar" }));

    await waitFor(() => {
      expect(screen.getByText("Login Route")).toBeInTheDocument();
    });
    expect(useAuthStore.getState().token).toBeNull();
  });

  it("shows error toast when delete account fails", async () => {
    const user = userEvent.setup();
    server.use(
      http.delete(/\/auth\/delete$/, () => {
        return HttpResponse.json({}, { status: 500 });
      }),
    );

    renderWithProviders(<PerfilPage />);
    await screen.findByText("Antonio");

    await user.click(screen.getByRole("button", { name: "Deletar conta" }));
    await user.click(screen.getByRole("button", { name: "Deletar" }));

    await waitFor(() => {
      expect(toastError).toHaveBeenCalledWith(
        "Não foi possível deletar a conta. Tente novamente.",
      );
    });
  });

  it("closes delete modal on close controls", async () => {
    const user = userEvent.setup();
    const { container } = renderWithProviders(<PerfilPage />);
    await screen.findByText("Antonio");

    await user.click(screen.getByRole("button", { name: "Deletar conta" }));
    expect(
      screen.getByText(/Tem certeza que deseja deletar sua conta\?/),
    ).toBeInTheDocument();

    await user.click(screen.getByRole("button", { name: "Cancelar" }));
    expect(
      screen.queryByText(/Tem certeza que deseja deletar sua conta\?/),
    ).not.toBeInTheDocument();

    await user.click(screen.getByRole("button", { name: "Deletar conta" }));
    const iconCloseButton = container.querySelector(
      "button.text-slate-400.hover\\:text-white.transition-colors",
    );
    expect(iconCloseButton).toBeTruthy();
    await user.click(iconCloseButton!);
    expect(
      screen.queryByText(/Tem certeza que deseja deletar sua conta\?/),
    ).not.toBeInTheDocument();
  });
});
