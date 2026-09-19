import { describe, expect, it, vi } from "vitest";
import { cn, timeToday } from "./utils";

describe("timeToday", () => {
  it("returns Bom dia between 6 and 11", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 0, 1, 8, 0, 0));

    expect(timeToday()).toBe("Bom dia");
    vi.useRealTimers();
  });

  it("returns Boa tarde between 12 and 17", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 0, 1, 15, 0, 0));

    expect(timeToday()).toBe("Boa tarde");
    vi.useRealTimers();
  });

  it("returns Boa noite in other hours", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 0, 1, 22, 0, 0));

    expect(timeToday()).toBe("Boa noite");
    vi.useRealTimers();
  });
});

describe("cn", () => {
  it("joins truthy classes and ignores falsy values", () => {
    expect(cn("a", false, undefined, "b")).toBe("a b");
  });
});
