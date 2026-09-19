import { api } from "../../../shared/lib/api";

export async function getMe() {
  const response = await api.get("/usuarios/me");

  return response.data;
}
