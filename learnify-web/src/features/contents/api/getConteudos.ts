import { api } from "../../../shared/lib/api";

export async function getConteudos(disciplinaId: string) {
  const response = await api.get(`/disciplinas/${disciplinaId}/conteudos`);

  return response.data;
}
