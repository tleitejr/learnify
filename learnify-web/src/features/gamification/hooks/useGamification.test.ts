import { act, renderHook } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { useGamification } from "./useGamification";

describe("useGamification", () => {
  it("starts with default values", () => {
    const { result } = renderHook(() => useGamification());

    expect(result.current.xp).toBe(250);
    expect(result.current.streak).toBe(5);
  });

  it("updates xp and streak", () => {
    const { result } = renderHook(() => useGamification());

    act(() => {
      result.current.addXP(100);
      result.current.increaseStreak();
    });

    expect(result.current.xp).toBe(350);
    expect(result.current.streak).toBe(6);
  });
});
