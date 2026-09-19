import userEvent from "@testing-library/user-event";
import { HttpResponse, http } from "msw";
import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { useAuthStore } from "../features/auth/authStore";
import { server } from "../test/mocks/server";
import { renderWithProviders, screen, waitFor } from "../test/test-utils";
import LoginPage from "./LoginPage";

describe("LoginPage", () => {
  it("logs in and redirects to dashboard", async () => {
    const user = userEvent.setup();
    renderWithProviders(
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<div>Dashboard OK</div>} />
      </Routes>,
      { route: "/login" },
    );

    await user.type(
      screen.getByPlaceholderText("Email"),
      "antonio@learnify.dev",
    );
    await user.type(screen.getByPlaceholderText("Senha"), "123456");
    await user.click(screen.getByRole("button", { name: "Continuar" }));

    await waitFor(() => {
      expect(screen.getByText("Dashboard OK")).toBeInTheDocument();
    });
    expect(useAuthStore.getState().token).toBe("token-123");
  });

  it("shows error message when login fails", async () => {
    const user = userEvent.setup();
    server.use(
      http.post(/\/auth\/login$/, () => {
        return HttpResponse.json({}, { status: 401 });
      }),
    );

    renderWithProviders(
      <Routes>
        <Route path="/login" element={<LoginPage />} />
      </Routes>,
      { route: "/login" },
    );

    await user.type(
      screen.getByPlaceholderText("Email"),
      "antonio@learnify.dev",
    );
    await user.type(screen.getByPlaceholderText("Senha"), "123456");
    await user.click(screen.getByRole("button", { name: "Continuar" }));

    expect(
      await screen.findByText("Email ou senha inválidos!"),
    ).toBeInTheDocument();
  });
});
