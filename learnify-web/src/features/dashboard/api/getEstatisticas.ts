import { api } from "../../../shared/lib/api";

export async function getEstatisticas() {
  const response = await api.get("/usuarios/me/estatisticas");

  return response.data;
}
