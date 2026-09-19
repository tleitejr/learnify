import { describe, expect, it } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import { RadialProgress } from "./RadialProgress";

describe("RadialProgress", () => {
  it("renders percentage text", () => {
    renderWithProviders(<RadialProgress percent={75} />);

    expect(screen.getByText("75%")).toBeInTheDocument();
  });
});
