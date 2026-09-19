import { api } from "../../../shared/lib/api";
import type { Pageable } from "../../../types/models";

export async function getRanking() {
  const response = await api.get("/ranking?page=0&size=5");

  return response.data as Pageable;
}
