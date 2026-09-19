import { useQuery } from "@tanstack/react-query";

import { getRanking } from "../features/ranking/api/getRanking";
import type { Ranking } from "../types/models";
import { TopCard } from "../shared/components/ui/TopCard";
import { LoadingState } from "../shared/components/ui/LoadingState";

export default function RankingPage() {
  const { data, isLoading } = useQuery({
    queryKey: ["ranking"],
    queryFn: getRanking,
  });

  if (isLoading) {
    return <LoadingState label="Carregando ranking..." />;
  }

  const topThree = data?.content.slice(0, 3) || [];
  const rest = data?.content.slice(3) || [];

  return (
    <div className="max-w-6xl mx-auto p-6 md:p-8">
      <section className="mb-10 bg-linear-to-r from-yellow-500 to-orange-500 rounded-3xl p-8 md:p-10">
        <p className="text-black/70 font-semibold mb-2">Ranking Global</p>
        <h1 className="text-4xl md:text-5xl font-bold text-black">
          Alunos em destaque 🏆
        </h1>
        <p className="text-black/70 mt-4 text-lg">
          Acompanhe os estudantes com maior progresso e pontuação.
        </p>
      </section>

      {topThree.length > 0 && (
        <section className="grid sm:grid-cols-2 md:grid-cols-3 gap-6 mb-12">
          {topThree.map((user: Ranking, index: number) => (
            <TopCard
              key={`top-${user.id}-${index}`}
              user={user}
              index={index}
            />
          ))}
        </section>
      )}

      <section className="bg-surface border border-line rounded-3xl overflow-hidden">
        <div className="p-6 border-b border-line">
          <h2 className="text-2xl font-bold">Leaderboard</h2>
          <p className="text-muted mt-2">
            Ranking baseado em XP e progresso acadêmico.
          </p>
        </div>

        <div className="divide-y divide-line">
          {rest.length > 0 ? (
            rest.map((user: Ranking, index: number) => (
              <div
                key={`${user.id}`}
                className="flex items-center justify-between gap-4 p-5 hover:bg-raised/40 transition"
              >
                <div className="flex items-center gap-4 min-w-0">
                  <div className="w-10 h-10 rounded-full bg-raised flex items-center justify-center font-bold text-slate-300 shrink-0">
                    {index + 4}
                  </div>
                  <div className="min-w-0">
                    <h3 className="font-semibold text-lg truncate">
                      {user.nomeUsuario}
                    </h3>
                    <p className="text-muted text-sm">Nível {user.nivel}</p>
                  </div>
                </div>
                <div className="text-right shrink-0">
                  <p className="font-bold text-primary-text">
                    {user.pontuacaoTotal} XP
                  </p>
                  <p className="text-sm text-faint">Progresso ativo</p>
                </div>
              </div>
            ))
          ) : (
            <p className="text-faint text-center p-8">
              Ainda não há outros participantes no ranking.
            </p>
          )}
        </div>
      </section>
    </div>
  );
}
