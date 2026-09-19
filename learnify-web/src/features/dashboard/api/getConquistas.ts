import { api } from "../../../shared/lib/api";
import type { Conquista } from "../../../types/models";

export async function getConquistas() {
  const response = await api.get("/usuarios/me/conquistas");

  return response.data as Conquista[];
}
