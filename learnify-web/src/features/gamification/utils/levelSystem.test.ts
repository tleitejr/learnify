import { describe, expect, it } from "vitest";
import { getLevel, getXPProgress } from "./levelSystem";

describe("levelSystem", () => {
  it("calculates level from xp", () => {
    expect(getLevel(0)).toBe(0);
    expect(getLevel(999)).toBe(0);
    expect(getLevel(1000)).toBe(1);
    expect(getLevel(2450)).toBe(2);
  });

  it("calculates xp progress in current level", () => {
    expect(getXPProgress(0)).toBe(0);
    expect(getXPProgress(999)).toBe(999);
    expect(getXPProgress(1000)).toBe(0);
    expect(getXPProgress(2450)).toBe(450);
  });
});
