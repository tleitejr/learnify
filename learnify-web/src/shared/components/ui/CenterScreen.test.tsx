import { describe, expect, it } from "vitest";
import { CenterScreen } from "./CenterScreen";
import { renderWithProviders, screen } from "../../../test/test-utils";

describe("CenterScreen", () => {
  it("renders children content", () => {
    renderWithProviders(
      <CenterScreen>
        <span>Conteúdo</span>
      </CenterScreen>,
    );

    expect(screen.getByText("Conteúdo")).toBeInTheDocument();
  });
});
