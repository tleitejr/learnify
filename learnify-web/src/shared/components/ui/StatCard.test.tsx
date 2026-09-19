import { Target } from "lucide-react";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { StatCard } from "./StatCard";

describe("StatCard", () => {
  it("shows skeleton when loading", () => {
    renderWithProviders(
      <StatCard label="Acertos" value={7} icon={<Target />} loading={true} />,
    );

    expect(screen.getByText("Acertos")).toBeInTheDocument();
    expect(screen.queryByText("7")).not.toBeInTheDocument();
  });

  it("shows value when not loading", () => {
    renderWithProviders(
      <StatCard label="Acertos" value={7} icon={<Target />} loading={false} />,
    );

    expect(screen.getByText("7")).toBeInTheDocument();
  });
});
