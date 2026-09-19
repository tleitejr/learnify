import { api } from "../../../shared/lib/api";
import type { Disciplina } from "../../../types/models";

export async function getDisciplinas() {
  const response = await api.get(`/disciplinas`);

  return response.data as Disciplina[];
}
