import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { DonutChart } from "./DonutChart";

describe("DonutChart", () => {
  it("shows fallback when total is zero", () => {
    renderWithProviders(<DonutChart acertos={0} erros={0} />);
    expect(screen.getByText("Sem dados")).toBeInTheDocument();
  });

  it("renders chart details when data exists", () => {
    renderWithProviders(<DonutChart acertos={8} erros={2} />);
    expect(screen.getByText("80%")).toBeInTheDocument();
    expect(screen.getByText("Acertos: 8")).toBeInTheDocument();
    expect(screen.getByText("Erros: 2")).toBeInTheDocument();
  });
});
