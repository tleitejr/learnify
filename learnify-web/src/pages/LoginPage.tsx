import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginRequest } from "../features/auth/api/login";
import { getMe } from "../features/perfil/api/getMe";
import { useAuthStore } from "../features/auth/authStore";
import AuthLayout from "../features/auth/components/AuthLayout";
import { Input } from "../shared/components/ui/Input";
import { Button } from "../shared/components/ui/Button";

export default function LoginPage() {
  const navigate = useNavigate();
  const setToken = useAuthStore((state) => state.setToken);
  const setUser = useAuthStore((state) => state.setUser);

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState("");

  async function handleLogin() {
    try {
      setLoading(true);
      setErro("");

      const auth = await loginRequest(email, senha);
      setToken(auth.token);
      const user = await getMe();
      setUser(user);
      navigate("/dashboard");
    } catch {
      setErro("Email ou senha inválidos!");
    } finally {
      setLoading(false);
    }
  }

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!loading) handleLogin();
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
    setErro("");
  };

  const handleSenhaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSenha(e.target.value);
    setErro("");
  };

  return (
    <AuthLayout title="Entrar">
      <form onSubmit={onSubmit} className="space-y-5" noValidate>
        <Input
          label="Email"
          type="email"
          autoComplete="email"
          placeholder="Email"
          value={email}
          onChange={handleEmailChange}
        />

        <Input
          label="Senha"
          type="password"
          autoComplete="current-password"
          placeholder="Senha"
          value={senha}
          onChange={handleSenhaChange}
          error={erro || undefined}
        />

        <Button type="submit" fullWidth loading={loading}>
          {loading ? "Entrando..." : "Continuar"}
        </Button>

        <p className="text-muted text-center mt-2">
          Não tem uma conta?{" "}
          <Link
            to="/signup"
            className="text-blue-400 hover:text-blue-300 transition rounded"
          >
            Crie uma
          </Link>
        </p>
      </form>
    </AuthLayout>
  );
}
