import { api } from "../../../shared/lib/api";

export async function loginRequest(email: string, senha: string) {
  const response = await api.post("/auth/login", {
    email,
    senha,
  });

  return response.data;
}
