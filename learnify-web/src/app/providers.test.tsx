import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";
import { AppProviders } from "./providers";

describe("AppProviders", () => {
  it("renders routed app content", async () => {
    render(<AppProviders />);
    expect(await screen.findByText("Entrar")).toBeInTheDocument();
  });
});
