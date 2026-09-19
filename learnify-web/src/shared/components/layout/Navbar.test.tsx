import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import Navbar from "./Navbar";

describe("Navbar", () => {
  it("renders search and xp", () => {
    renderWithProviders(<Navbar />);
    expect(
      screen.getByPlaceholderText("Buscar conteúdos..."),
    ).toBeInTheDocument();
    expect(screen.getByText("250 XP")).toBeInTheDocument();
  });
});
