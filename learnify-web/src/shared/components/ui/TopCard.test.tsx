import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { TopCard } from "./TopCard";

const user = {
  id: "1",
  nomeUsuario: "Ana",
  nivel: 8,
  pontuacaoTotal: 8000,
};

describe("TopCard", () => {
  it("renders user info", () => {
    renderWithProviders(<TopCard user={user} index={0} />);
    expect(screen.getByText("Ana")).toBeInTheDocument();
    expect(screen.getByText("Nível 8")).toBeInTheDocument();
    expect(screen.getByText("8000")).toBeInTheDocument();
  });

  it("renders without medal icon for unsupported index", () => {
    const { container } = renderWithProviders(
      <TopCard user={user} index={99} />,
    );
    expect(container).toHaveTextContent("Ana");
  });
});
