import { describe, expect, it, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import App from "./App";

vi.mock("./app/providers", () => ({
  AppProviders: () => <div>Providers Mock</div>,
}));

describe("App", () => {
  it("renders app providers", () => {
    render(<App />);
    expect(screen.getByText("Providers Mock")).toBeInTheDocument();
  });
});
