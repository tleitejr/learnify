import { useQuery } from "@tanstack/react-query";
import { useNavigate, useParams } from "react-router-dom";
import { CheckCircle } from "lucide-react";
import { getConteudos } from "../features/contents/api/getConteudos";
import type { ConteudosPageProps, Conteudo } from "../types/models";
import { cn } from "../shared/utils/utils";
import { CenterScreen } from "../shared/components/ui/CenterScreen";
import { LoadingState } from "../shared/components/ui/LoadingState";

export default function ConteudosPage({
  disciplinaId: propDisciplinaId,
  isModal = false,
}: ConteudosPageProps = {}) {
  const navigate = useNavigate();
  const params = useParams();
  const disciplinaId = propDisciplinaId ?? params.disciplinaId;

  const { data: conteudos, isLoading } = useQuery<Conteudo[]>({
    queryKey: ["conteudos", disciplinaId],
    queryFn: () => getConteudos(disciplinaId as string),
    enabled: !!disciplinaId,
  });

  if (!disciplinaId) {
    return <CenterScreen>Disciplina não informada</CenterScreen>;
  }

  if (isLoading) {
    return <LoadingState label="Carregando conteúdos..." />;
  }

  return (
    <div className={cn("text-white", isModal ? "p-4 md:p-6" : "p-6 md:p-8")}>
      <div className="max-w-7xl mx-auto">
        <div className="mb-6">
          <h2 className="text-2xl md:text-3xl font-bold">Conteúdos</h2>
          <p className="text-muted mt-2">Escolha um conteúdo para começar.</p>
        </div>

        {conteudos && conteudos.length > 0 ? (
          <section className="grid md:grid-cols-2 xl:grid-cols-3 gap-4 md:gap-6">
            {conteudos.map((item: Conteudo) => (
              <button
                key={item.id}
                onClick={() => {
                  if (!item.concluido) {
                    navigate(`/conteudos/${item.id}/quiz`);
                  }
                }}
                disabled={item.concluido}
                className={cn(
                  "group relative bg-surface border border-line rounded-3xl p-6 text-left transition-all duration-300",
                  item.concluido
                    ? "opacity-60 cursor-not-allowed"
                    : "hover:border-primary-accent/70 hover:shadow-lg hover:shadow-primary/10 hover:-translate-y-1",
                )}
              >
                <div className="flex justify-between items-start gap-2">
                  <h3 className="text-2xl font-bold">{item.titulo}</h3>
                  {item.concluido && (
                    <span className="flex items-center gap-1.5 px-3 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-sm font-medium shrink-0">
                      <CheckCircle size={14} aria-hidden />
                      Concluído
                    </span>
                  )}
                </div>
              </button>
            ))}
          </section>
        ) : (
          <p className="text-faint text-center py-8">
            Nenhum conteúdo disponível nesta disciplina ainda.
          </p>
        )}
      </div>
    </div>
  );
}
