import { api } from "../../../shared/lib/api";

export async function excluirConta() {
  const response = await api.delete("/auth/delete");
  return response.data as string;
}
