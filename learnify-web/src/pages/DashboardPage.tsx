import { useQuery } from "@tanstack/react-query";
import { TrendingUp, Target, Award, Zap, Trophy } from "lucide-react";

import { getMe } from "../features/perfil/api/getMe";
import { getEstatisticas } from "../features/dashboard/api/getEstatisticas";
import { getConquistas } from "../features/dashboard/api/getConquistas";
import { getRanking } from "../features/ranking/api/getRanking";
import {
  type Conquista,
  type EstatisticasUsuario,
  type Pageable,
  type Ranking,
  type Usuario,
} from "../types/models";
import { timeToday } from "../shared/utils/utils";
import { Skeleton } from "../shared/components/ui/Skeleton";
import { DonutChart } from "../shared/components/ui/DonutChart";
import { RadialProgress } from "../shared/components/ui/RadialProgress";
import { StatCard } from "../shared/components/ui/StatCard";

export default function DashboardPage() {
  const { data: user, isLoading: userLoading } = useQuery<Usuario>({
    queryKey: ["me"],
    queryFn: getMe,
  });

  const { data: stats, isLoading: statsLoading } =
    useQuery<EstatisticasUsuario>({
      queryKey: ["stats"],
      queryFn: getEstatisticas,
    });

  const { data: conquistas, isLoading: conquistasLoading } = useQuery<
    Conquista[]
  >({
    queryKey: ["conquistas"],
    queryFn: getConquistas,
  });

  const { data: ranking, isLoading: rankingLoading } = useQuery<Pageable>({
    queryKey: ["ranking"],
    queryFn: getRanking,
  });

  const progressPercent = user
    ? Math.min(100, (user.pontuacaoTotal % 1000) / 10)
    : 0;
  const xpToNextLevel = 1000 - ((user?.pontuacaoTotal ?? 0) % 1000);

  const rankingList = ranking?.content.slice(0, 5) || [];

  return (
    <div className="p-6 md:p-8 max-w-7xl mx-auto space-y-8">
      <header className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div className="flex items-center gap-4">
          <div className="w-12 h-12 rounded-full bg-primary-accent/20 flex items-center justify-center text-primary-text font-bold text-xl">
            {userLoading ? (
              <Skeleton className="w-8 h-8 rounded-full" />
            ) : (
              user?.nome.charAt(0)
            )}
          </div>
          <div>
            <p className="text-muted text-sm">{timeToday()}</p>
            {userLoading ? (
              <Skeleton className="h-8 w-40 mt-1" />
            ) : (
              <h2 className="text-2xl md:text-3xl font-bold">
                Olá, {user?.nome}
              </h2>
            )}
          </div>
        </div>
        <div className="flex items-center gap-4">
          <div className="bg-surface border border-line rounded-2xl px-4 py-2 text-sm">
            <span className="text-muted">Nível </span>
            <span className="text-primary-text font-bold">
              {user?.nivel ?? "—"}
            </span>
          </div>
          <div className="bg-surface border border-line rounded-2xl px-4 py-2 text-sm">
            <span className="text-muted">XP Total </span>
            <span className="text-white font-bold">
              {user?.pontuacaoTotal ?? "—"}
            </span>
          </div>
        </div>
      </header>

      <section className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          label="Desempenho"
          value={stats?.desempenho ?? "—"}
          icon={<TrendingUp className="text-emerald-400" />}
          loading={statsLoading}
        />
        <StatCard
          label="Acertos"
          value={stats?.acertos ?? "—"}
          icon={<Target className="text-primary-text" />}
          loading={statsLoading}
        />
        <StatCard
          label="Média Pontos"
          value={stats?.mediaPontos ?? "—"}
          icon={<Zap className="text-amber-400" />}
          loading={statsLoading}
        />
        <StatCard
          label="Conquistas"
          value={conquistas?.length ?? 0}
          icon={<Award className="text-yellow-400" />}
          loading={conquistasLoading}
        />
      </section>

      <div className="grid lg:grid-cols-3 gap-6">
        <div className="bg-surface border border-line rounded-2xl p-6">
          <h3 className="text-lg font-semibold mb-4">Progresso do Nível</h3>
          {userLoading ? (
            <Skeleton className="h-40 w-full rounded-xl" />
          ) : (
            <div className="flex flex-col items-center">
              <RadialProgress percent={progressPercent} />
              <p className="text-muted text-sm mt-4">
                Faltam {xpToNextLevel} XP para o próximo nível
              </p>
            </div>
          )}
        </div>

        <div className="bg-surface border border-line rounded-2xl p-6">
          <h3 className="text-lg font-semibold mb-4">Acertos vs Erros</h3>
          {statsLoading ? (
            <Skeleton className="h-40 w-full rounded-xl" />
          ) : (
            <DonutChart
              acertos={stats?.acertos || 0}
              erros={stats?.erros || 0}
            />
          )}
        </div>

        <div className="bg-surface border border-line rounded-2xl p-6">
          <h3 className="text-lg font-semibold mb-4 flex items-center gap-2">
            <Trophy size={18} className="text-yellow-400" aria-hidden />
            Top Ranking
          </h3>
          {rankingLoading ? (
            <div className="space-y-3">
              {Array.from({ length: 4 }).map((_, i) => (
                <Skeleton key={`rank-skel-${i}`} className="h-8 w-full" />
              ))}
            </div>
          ) : (
            <div className="space-y-3">
              {rankingList.map((item: Ranking, index: number) => (
                <div
                  key={`${item.id}`}
                  className="flex items-center justify-between text-sm"
                >
                  <div className="flex items-center gap-3">
                    <span
                      className={`font-bold w-6 text-center ${
                        index === 0
                          ? "text-yellow-400"
                          : index === 1
                            ? "text-slate-300"
                            : index === 2
                              ? "text-amber-700"
                              : "text-faint"
                      }`}
                    >
                      {index + 1}
                    </span>
                    <span>{item.nomeUsuario}</span>
                  </div>
                  <span className="text-primary-text font-medium">
                    {item.pontuacaoTotal} XP
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <section>
        <h3 className="text-xl font-bold mb-4 flex items-center gap-2">
          <Award size={20} className="text-yellow-400" aria-hidden />
          Conquistas recentes
        </h3>
        {conquistasLoading ? (
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {Array.from({ length: 3 }).map((_, i) => (
              <Skeleton key={`conq-skel-${i}`} className="h-24 rounded-2xl" />
            ))}
          </div>
        ) : conquistas && conquistas.length > 0 ? (
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {conquistas.slice(0, 3).map((c) => (
              <div
                key={c.id}
                className="bg-surface border border-line rounded-2xl p-4 flex items-center gap-4 hover:border-primary-accent transition group"
              >
                <div className="w-10 h-10 rounded-xl bg-yellow-500/10 flex items-center justify-center group-hover:scale-110 transition-transform shrink-0">
                  <Trophy className="text-yellow-400" size={18} aria-hidden />
                </div>
                <div>
                  <p className="font-semibold">{c.titulo}</p>
                  <p className="text-sm text-muted">{c.descricao}</p>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-faint text-center py-8">
            Nenhuma conquista ainda.
          </p>
        )}
      </section>
    </div>
  );
}
