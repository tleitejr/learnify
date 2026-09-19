import { HttpResponse, http } from "msw";

export const authHandlers = [
  http.post(/\/auth\/login$/, async () => {
    return HttpResponse.json({ token: "token-123" });
  }),
  http.post(/\/auth\/signup$/, async () => {
    return HttpResponse.json({ token: "token-123" });
  }),
  http.post(/\/auth\/logout$/, async () => {
    return HttpResponse.json({
      message: "Logout realizado com sucesso.",
      dataLogout: "2026-01-01T00:00:00.000Z",
    });
  }),
];
