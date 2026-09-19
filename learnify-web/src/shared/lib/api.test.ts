import { HttpResponse, http } from "msw";
import { afterEach, describe, expect, it, vi } from "vitest";
import { api } from "./api";
import { server } from "../../test/mocks/server";

describe("api client", () => {
  afterEach(() => {
    vi.restoreAllMocks();
  });

  it("adds auth header when token exists", async () => {
    localStorage.setItem("token", "my-token");

    let capturedAuthHeader: string | null = null;
    server.use(
      http.get(/\/header-test$/, ({ request }) => {
        capturedAuthHeader = request.headers.get("authorization");
        return HttpResponse.json({ ok: true });
      }),
    );

    await api.get("/header-test");
    expect(capturedAuthHeader).toBe("Bearer my-token");
  });

  it("clears token on 401 response", async () => {
    localStorage.setItem("token", "my-token");
    const removeSpy = vi.spyOn(Storage.prototype, "removeItem");

    server.use(
      http.get(/\/unauthorized$/, () => {
        return HttpResponse.json({ error: "Unauthorized" }, { status: 401 });
      }),
    );

    await expect(api.get("/unauthorized")).rejects.toBeTruthy();
    expect(removeSpy).toHaveBeenCalledWith("token");
  });
});
