import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import StreakCard from "./StreakCard";

describe("StreakCard", () => {
  it("shows streak days", () => {
    renderWithProviders(<StreakCard days={10} />);
    expect(screen.getByText("10 dias")).toBeInTheDocument();
  });
});
