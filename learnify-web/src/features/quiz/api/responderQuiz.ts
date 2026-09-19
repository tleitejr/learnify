import { api } from "../../../shared/lib/api";

type RespostaQuizPayload = {
  questaoId: string;
  alternativaSelecionadaId: string;
};

export async function responderQuiz(payload: RespostaQuizPayload) {
  const response = await api.post("/quiz/responder", payload);

  return response.data;
}
