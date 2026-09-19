import { Crown } from "lucide-react";
import type { Ranking } from "../../../types/models";

export function TopCard({ user, index }: { user: Ranking; index: number }) {
  const medalIcons: Record<number, React.ReactNode> = {
    0: <Crown className="text-yellow-300" />,
    1: <Crown className="text-slate-300" />,
    2: <Crown className="text-orange-400" />,
  };

  return (
    <div className="bg-surface border border-line rounded-3xl p-8 text-center relative overflow-hidden">
      <div className="absolute top-4 right-4" aria-hidden>
        {medalIcons[index]}
      </div>
      <div className="w-20 h-20 rounded-full bg-primary/20 mx-auto mb-5 flex items-center justify-center text-3xl font-bold text-primary-text">
        {user.nomeUsuario.charAt(0)}
      </div>
      <h3 className="text-2xl font-bold">{user.nomeUsuario}</h3>
      <p className="text-muted mt-2">Nível {user.nivel}</p>
      <div className="mt-6">
        <p className="text-4xl font-bold text-yellow-400">
          {user.pontuacaoTotal}
        </p>
        <p className="text-faint text-sm mt-1">XP total</p>
      </div>
    </div>
  );
}
