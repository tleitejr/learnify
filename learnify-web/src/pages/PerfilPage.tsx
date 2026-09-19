import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { Target, BookOpen, Award, Flame, Trophy, Trash } from "lucide-react";
import { getMe } from "../features/perfil/api/getMe";
import type { Conquista, EstatisticasUsuario, Usuario } from "../types/models";
import { getEstatisticas } from "../features/dashboard/api/getEstatisticas";
import { getConquistas } from "../features/dashboard/api/getConquistas";
import { StatCardProfile } from "../shared/components/ui/StatCardProfile";
import { excluirConta } from "../features/perfil/api/excluirConta";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { useAuthStore } from "../features/auth/authStore";
import { Modal } from "../shared/components/ui/Modal";
import { Button } from "../shared/components/ui/Button";
import { LoadingState } from "../shared/components/ui/LoadingState";

export default function PerfilPage() {
  const navigate = useNavigate();
  const logout = useAuthStore((state) => state.logout);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const { data: user, isLoading: userLoading } = useQuery<Usuario>({
    queryKey: ["me"],
    queryFn: getMe,
  });

  const { data: estatisticas } = useQuery<EstatisticasUsuario>({
    queryKey: ["estatisticas"],
    queryFn: getEstatisticas,
  });

  const { data: conquistas, isLoading: conquistasLoading } = useQuery<
    Conquista[]
  >({
    queryKey: ["conquistas"],
    queryFn: getConquistas,
  });

  const handleExcluirConta = async () => {
    try {
      await excluirConta();
      logout();
      setShowDeleteModal(false);
      navigate("/login");
    } catch {
      toast.error("Não foi possível deletar a conta. Tente novamente.");
    }
  };

  if (userLoading) {
    return <LoadingState label="Carregando perfil..." />;
  }

  return (
    <div className="max-w-7xl mx-auto p-6 md:p-8">
      <section className="bg-linear-to-r from-violet-600 to-indigo-600 rounded-3xl p-6 md:p-10 mb-10">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-8">
          <div className="flex items-center gap-6">
            <div
              className="w-20 h-20 md:w-24 md:h-24 rounded-full bg-white/10 flex items-center justify-center text-4xl font-bold shrink-0"
              aria-hidden
            >
              {user?.nome.charAt(0)}
            </div>
            <div className="min-w-0">
              <p className="text-white/70 mb-2 truncate">{user?.email}</p>
              <h1 className="text-3xl md:text-4xl font-bold break-words">
                {user?.nome}
              </h1>
            </div>
          </div>
          <div className="bg-black/20 rounded-3xl p-6 md:min-w-55">
            <p className="text-white/70 mb-2">XP Total</p>
            <h2 className="text-5xl font-bold">{user?.pontuacaoTotal}</h2>
            <p className="mt-3 text-white/70">Nível {user?.nivel}</p>
          </div>
        </div>
      </section>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-10">
        <section className="bg-surface border border-line rounded-3xl p-6 md:p-8">
          <div className="flex items-center gap-3 mb-6">
            <Award className="text-yellow-400" aria-hidden />
            <h2 className="text-2xl font-bold">Conquistas</h2>
          </div>
          <div className="space-y-4">
            {conquistasLoading ? (
              <LoadingState label="Carregando conquistas..." />
            ) : conquistas && conquistas.length > 0 ? (
              conquistas.map((item: Conquista) => (
                <div key={item.id} className="bg-raised rounded-2xl p-4">
                  <div className="flex items-center gap-3">
                    <div className="w-12 h-12 rounded-xl bg-yellow-500/10 flex items-center justify-center shrink-0">
                      <Trophy className="text-yellow-400" aria-hidden />
                    </div>
                    <div>
                      <h3 className="font-semibold">{item.titulo}</h3>
                      <p className="text-sm text-muted">{item.descricao}</p>
                    </div>
                  </div>
                </div>
              ))
            ) : (
              <p className="text-faint text-center py-8">
                Nenhuma conquista ainda.
              </p>
            )}
          </div>
        </section>

        <div className="flex flex-col gap-5">
          <StatCardProfile
            icon={<BookOpen aria-hidden />}
            title="Desempenho"
            value={estatisticas?.desempenho}
          />
          <StatCardProfile
            icon={<Target aria-hidden />}
            title="Pontuação Total"
            value={estatisticas?.pontuacaoTotal}
          />
          <StatCardProfile
            icon={<Flame aria-hidden />}
            title="Nível"
            value={estatisticas?.nivel}
          />
        </div>
      </div>

      <section className="bg-surface border border-line rounded-3xl p-6 md:p-8 mt-6">
        <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <div>
            <h3 className="text-lg font-semibold text-danger-text flex items-center gap-2">
              <Trash size={20} aria-hidden />
              Zona de Perigo
            </h3>
            <p className="text-muted text-sm">
              Ao deletar sua conta, todos os dados serão perdidos
              permanentemente.
            </p>
          </div>
          <Button
            variant="danger"
            onClick={() => setShowDeleteModal(true)}
            className="shrink-0"
          >
            <Trash size={18} aria-hidden />
            Deletar conta
          </Button>
        </div>
      </section>

      <Modal
        open={showDeleteModal}
        onClose={() => setShowDeleteModal(false)}
        title="Deletar conta"
      >
        <div className="p-6">
          <h3 className="text-xl font-bold text-danger-text flex items-center gap-2 mb-4 pr-8">
            <Trash size={24} aria-hidden />
            Deletar conta
          </h3>
          <p className="text-slate-300 mb-6">
            Tem certeza que deseja deletar sua conta? Esta ação é{" "}
            <strong>irreversível</strong> e todos os seus dados serão removidos
            permanentemente.
          </p>
          <div className="flex flex-col sm:flex-row gap-3 sm:gap-4">
            <Button
              variant="secondary"
              onClick={() => setShowDeleteModal(false)}
              fullWidth
            >
              Cancelar
            </Button>
            <Button variant="danger" onClick={handleExcluirConta} fullWidth>
              <Trash size={18} aria-hidden />
              Deletar
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
