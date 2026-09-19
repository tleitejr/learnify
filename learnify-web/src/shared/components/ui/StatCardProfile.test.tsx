import { Flame } from "lucide-react";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { StatCardProfile } from "./StatCardProfile";

describe("StatCardProfile", () => {
  it("renders title and value", () => {
    renderWithProviders(
      <StatCardProfile icon={<Flame />} title="Nível" value={5} />,
    );

    expect(screen.getByText("Nível")).toBeInTheDocument();
    expect(screen.getByText("5")).toBeInTheDocument();
  });
});
