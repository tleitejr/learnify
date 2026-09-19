import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../test/test-utils";
import { useAuthStore } from "../../features/auth/authStore";
import PublicRoute from "./PublicRoute";

describe("PublicRoute", () => {
  it("renders children when user is not authenticated", () => {
    useAuthStore.setState({ token: null, user: null });

    renderWithProviders(
      <Routes>
        <Route
          path="/login"
          element={
            <PublicRoute>
              <div>Tela pública</div>
            </PublicRoute>
          }
        />
      </Routes>,
      { route: "/login" },
    );

    expect(screen.getByText("Tela pública")).toBeInTheDocument();
  });

  it("redirects authenticated users to dashboard", () => {
    useAuthStore.setState({ token: "token-1", user: null });

    renderWithProviders(
      <Routes>
        <Route
          path="/login"
          element={
            <PublicRoute>
              <div>Tela pública</div>
            </PublicRoute>
          }
        />
        <Route path="/dashboard" element={<div>Dashboard</div>} />
      </Routes>,
      { route: "/login" },
    );

    expect(screen.getByText("Dashboard")).toBeInTheDocument();
  });
});
