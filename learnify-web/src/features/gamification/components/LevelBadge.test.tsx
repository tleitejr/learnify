import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import LevelBadge from "./LevelBadge";

describe("LevelBadge", () => {
  it("renders level based on xp", () => {
    renderWithProviders(<LevelBadge xp={2300} />);
    expect(screen.getByText("Level 2")).toBeInTheDocument();
  });
});
