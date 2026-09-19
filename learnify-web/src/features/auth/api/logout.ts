import { api } from "../../../shared/lib/api";
import type { LogoutResponse } from "../../../types/models";

export async function logoutRequest() {
  const response = await api.post("/auth/logout");

  return response.data as LogoutResponse;
}
