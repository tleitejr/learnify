import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import XPBar from "./XPBar";

describe("XPBar", () => {
  it("shows current xp progress", () => {
    renderWithProviders(<XPBar xp={2450} />);
    expect(screen.getByText("450/1000")).toBeInTheDocument();
  });
});
