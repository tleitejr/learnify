import { act } from "@testing-library/react";
import { describe, expect, it, vi } from "vitest";
import { renderWithProviders, screen } from "../../../test/test-utils";
import RewardPopup from "./RewardPopup";

describe("RewardPopup", () => {
  it("disappears after timeout", () => {
    vi.useFakeTimers();
    renderWithProviders(<RewardPopup xp={100} />);

    expect(screen.getByText("+100 XP 🎉")).toBeInTheDocument();

    act(() => {
      vi.advanceTimersByTime(2000);
    });
    expect(screen.queryByText("+100 XP 🎉")).not.toBeInTheDocument();
    vi.useRealTimers();
  });
});
