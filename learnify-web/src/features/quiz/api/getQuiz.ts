import { api } from "../../../shared/lib/api";

export async function getQuiz(conteudoId: string) {
  const response = await api.get(`/conteudos/${conteudoId}/quiz`);

  return response.data;
}
