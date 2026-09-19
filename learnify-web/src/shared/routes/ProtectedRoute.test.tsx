import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../test/test-utils";
import { useAuthStore } from "../../features/auth/authStore";
import ProtectedRoute from "./ProtectedRoute";

describe("ProtectedRoute", () => {
  it("redirects unauthenticated users to login", () => {
    useAuthStore.setState({ token: null, user: null });

    renderWithProviders(
      <Routes>
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <div>Dashboard privado</div>
            </ProtectedRoute>
          }
        />
        <Route path="/login" element={<div>Página Login</div>} />
      </Routes>,
      { route: "/dashboard" },
    );

    expect(screen.getByText("Página Login")).toBeInTheDocument();
  });

  it("renders children for authenticated users", () => {
    useAuthStore.setState({ token: "token-1", user: null });

    renderWithProviders(
      <Routes>
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <div>Dashboard privado</div>
            </ProtectedRoute>
          }
        />
      </Routes>,
      { route: "/dashboard" },
    );

    expect(screen.getByText("Dashboard privado")).toBeInTheDocument();
  });
});
