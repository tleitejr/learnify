import { describe, expect, it, vi } from "vitest";
import { useAuthStore } from "./authStore";

describe("useAuthStore", () => {
  it("sets and clears token/user", () => {
    useAuthStore.getState().setToken("abc");
    useAuthStore.getState().setUser({
      id: 1,
      nome: "Antonio",
      email: "antonio@learnify.dev",
      nivel: 2,
      pontuacaoTotal: 1000,
    });

    expect(useAuthStore.getState().token).toBe("abc");
    expect(useAuthStore.getState().user?.nome).toBe("Antonio");

    useAuthStore.getState().logout();

    expect(useAuthStore.getState().token).toBeNull();
    expect(useAuthStore.getState().user).toBeNull();
  });

  it("reads initial token from localStorage", async () => {
    localStorage.setItem("token", "seed-token");
    vi.resetModules();
    const { useAuthStore: freshStore } = await import("./authStore");

    expect(freshStore.getState().token).toBe("seed-token");
  });
});
