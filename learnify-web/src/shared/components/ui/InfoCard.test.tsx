import { BookOpen } from "lucide-react";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { InfoCard } from "./InfoCard";

describe("InfoCard", () => {
  it("renders icon and title", () => {
    renderWithProviders(
      <InfoCard icon={<BookOpen data-testid="icon" />} title="Teste" />,
    );

    expect(screen.getByText("Teste")).toBeInTheDocument();
    expect(screen.getByTestId("icon")).toBeInTheDocument();
  });
});
