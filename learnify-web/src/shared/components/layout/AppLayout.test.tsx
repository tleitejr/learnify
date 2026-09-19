import { Route, Routes } from "react-router-dom";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import AppLayout from "./AppLayout";

describe("AppLayout", () => {
  it("renders outlet content", () => {
    renderWithProviders(
      <Routes>
        <Route element={<AppLayout />}>
          <Route path="/dashboard" element={<div>Dashboard Outlet</div>} />
        </Route>
      </Routes>,
      { route: "/dashboard" },
    );

    expect(screen.getByText("Dashboard Outlet")).toBeInTheDocument();
  });
});
