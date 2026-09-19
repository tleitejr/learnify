import { api } from "../../../shared/lib/api";

export async function signupRequest(
  nome: string,
  email: string,
  senha: string,
) {
  const response = await api.post("/auth/signup", {
    nome,
    email,
    senha,
  });

  return response.data;
}
