import { describe, expect, it } from "vitest";
import { renderWithProviders } from "../../../test/test-utils";
import { Skeleton } from "./Skeleton";

describe("Skeleton", () => {
  it("renders with provided classes", () => {
    const { container } = renderWithProviders(<Skeleton className="h-8 w-8" />);

    expect(container.firstChild).toHaveClass("animate-pulse");
    expect(container.firstChild).toHaveClass("h-8");
    expect(container.firstChild).toHaveClass("w-8");
  });
});
