import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getMe } from "../features/perfil/api/getMe";
import { useAuthStore } from "../features/auth/authStore";
import { signupRequest } from "../features/auth/api/signup";
import AuthLayout from "../features/auth/components/AuthLayout";
import { Input } from "../shared/components/ui/Input";
import { Button } from "../shared/components/ui/Button";

export default function SignupPage() {
  const navigate = useNavigate();
  const setToken = useAuthStore((state) => state.setToken);
  const setUser = useAuthStore((state) => state.setUser);

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState("");

  async function handleSignup() {
    try {
      setLoading(true);
      setErro("");

      const auth = await signupRequest(nome, email, senha);
      setToken(auth.token);
      const user = await getMe();
      setUser(user);
      navigate("/dashboard");
    } catch {
      setErro("Email já cadastrado!");
    } finally {
      setLoading(false);
    }
  }

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!loading) handleSignup();
  };

  const handleNomeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNome(e.target.value);
    setErro("");
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
    <AuthLayout title="Criar conta">
      <form onSubmit={onSubmit} className="space-y-5" noValidate>
        <Input
          label="Nome"
          type="text"
          autoComplete="name"
          placeholder="Nome"
          value={nome}
          onChange={handleNomeChange}
        />

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
          autoComplete="new-password"
          placeholder="Senha"
          value={senha}
          onChange={handleSenhaChange}
          error={erro || undefined}
        />

        <Button type="submit" fullWidth loading={loading}>
          {loading ? "Criando..." : "Continuar"}
        </Button>

        <p className="text-muted text-center mt-2">
          Já tem uma conta?{" "}
          <Link
            to="/login"
            className="text-blue-400 hover:text-blue-300 transition rounded"
          >
            Entrar
          </Link>
        </p>
      </form>
    </AuthLayout>
  );
}
