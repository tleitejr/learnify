import userEvent from "@testing-library/user-event";
import { HttpResponse, http } from "msw";
import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { useAuthStore } from "../../../features/auth/authStore";
import { server } from "../../../test/mocks/server";
import { renderWithProviders, screen, waitFor } from "../../../test/test-utils";
import Sidebar from "./Sidebar";

describe("Sidebar", () => {
  it("toggles collapse state and persists in localStorage", async () => {
    const user = userEvent.setup();
    renderWithProviders(<Sidebar />);

    const toggleButton = screen.getByRole("button", { name: "Minimizar menu" });
    await user.click(toggleButton);

    expect(localStorage.getItem("sidebar-collapsed")).toBe("true");
    expect(
      screen.getByRole("button", { name: "Expandir menu" }),
    ).toBeInTheDocument();
  });

  it("logs out and redirects even when API fails", async () => {
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

    server.use(
      http.post(/\/auth\/logout$/, () => {
        return HttpResponse.json({}, { status: 500 });
      }),
    );

    renderWithProviders(
      <Routes>
        <Route path="/dashboard" element={<Sidebar />} />
        <Route path="/login" element={<div>Login Route</div>} />
      </Routes>,
      { route: "/dashboard" },
    );

    await user.click(screen.getByRole("button", { name: "Sair" }));

    await waitFor(() => {
      expect(screen.getByText("Login Route")).toBeInTheDocument();
    });
    expect(useAuthStore.getState().token).toBeNull();
  });

  it("logs out and redirects when API succeeds", async () => {
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
        <Route path="/dashboard" element={<Sidebar />} />
        <Route path="/login" element={<div>Login Route</div>} />
      </Routes>,
      { route: "/dashboard" },
    );

    await user.click(screen.getByRole("button", { name: "Sair" }));
    await waitFor(() => {
      expect(screen.getByText("Login Route")).toBeInTheDocument();
    });
    expect(useAuthStore.getState().token).toBeNull();
  });
});
