import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";
import { AlertTriangle } from "lucide-react";

import { getQuiz } from "../features/quiz/api/getQuiz";
import { responderQuiz } from "../features/quiz/api/responderQuiz";
import type { Questao, ResultadoQuiz } from "../types/models";
import { CenterScreen } from "../shared/components/ui/CenterScreen";
import { Button } from "../shared/components/ui/Button";
import { Card } from "../shared/components/ui/Card";

export default function QuizPage() {
  const navigate = useNavigate();
  const { conteudoId } = useParams();
  const queryClient = useQueryClient();

  const [showWarning, setShowWarning] = useState(true);

  const [index, setIndex] = useState(0);
  const [selected, setSelected] = useState<string | null>(null);
  const [score, setScore] = useState(0);
  const [finished, setFinished] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const { data, isLoading, isError, refetch } = useQuery<Questao[]>({
    queryKey: ["quiz", conteudoId],
    queryFn: () => getQuiz(conteudoId!),
    enabled: !!conteudoId && !showWarning,
    staleTime: 1000 * 60 * 5,
  });

  const questoes = data ?? [];
  const questao = questoes[index];
  const progress =
    questoes.length > 0 ? ((index + 1) / questoes.length) * 100 : 0;

  if (!conteudoId) {
    return <CenterScreen>Atividade inválida.</CenterScreen>;
  }

  if (showWarning) {
    return (
      <CenterScreen>
        <Card className="p-10 text-center max-w-lg w-full">
          <div className="flex justify-center mb-6">
            <AlertTriangle className="w-16 h-16 text-yellow-500" aria-hidden />
          </div>
          <h2 className="text-2xl font-bold text-white mb-4">Atenção</h2>
          <p className="text-slate-300 mb-8">
            Assim que clicar em <strong>Iniciar</strong>, não será possível
            retornar.
          </p>
          <div className="grid grid-cols-2 gap-4">
            <Button variant="secondary" onClick={() => navigate(-1)}>
              Voltar
            </Button>
            <Button onClick={() => setShowWarning(false)}>Iniciar</Button>
          </div>
        </Card>
      </CenterScreen>
    );
  }

  if (isLoading) {
    return <CenterScreen>Carregando quiz...</CenterScreen>;
  }

  if (isError) {
    return (
      <CenterScreen>
        <div className="text-center">
          <p className="mb-4">Erro ao carregar quiz.</p>
          <Button onClick={() => refetch()}>Tentar novamente</Button>
        </div>
      </CenterScreen>
    );
  }

  if (!questao && !finished) {
    return <CenterScreen>Nenhuma questão encontrada.</CenterScreen>;
  }

  async function confirmarResposta() {
    if (!selected || !questao) return;

    try {
      setIsSubmitting(true);
      const result: ResultadoQuiz = await responderQuiz({
        questaoId: questao.id,
        alternativaSelecionadaId: selected,
      });

      if (result.correta) {
        setScore((prev) => prev + result.pontosGanhos);
        toast.success(`+${result.pontosGanhos} XP`);
      } else {
        toast.error("Resposta incorreta");
      }

      if (result.conquistaDesbloqueada) {
        toast.success(`🏆 ${result.conquistaDesbloqueada}`);
      }

      await Promise.all([
        queryClient.invalidateQueries({ queryKey: ["me"] }),
        queryClient.invalidateQueries({ queryKey: ["stats"] }),
        queryClient.invalidateQueries({ queryKey: ["conquistas"] }),
        queryClient.invalidateQueries({ queryKey: ["ranking"] }),
        queryClient.invalidateQueries({ queryKey: ["conteudos"] }),
      ]);

      const isLastQuestion = index + 1 >= questoes.length;
      if (isLastQuestion) {
        setFinished(true);
      } else {
        setIndex((prev) => prev + 1);
        setSelected(null);
      }
    } catch {
      toast.error("Erro ao responder quiz");
    } finally {
      setIsSubmitting(false);
    }
  }

  if (finished) {
    return (
      <CenterScreen>
        <Card className="p-10 text-center max-w-lg w-full">
          <h1 className="text-4xl font-bold mb-4 text-white">
            Quiz Finalizado 🎉
          </h1>
          <p className="text-muted mb-6">Você ganhou:</p>
          <h2 className="text-6xl font-bold text-primary-text mb-8">
            {score} XP
          </h2>
          <div className="grid gap-3">
            <Button onClick={() => navigate(-1)}>Continuar</Button>
          </div>
        </Card>
      </CenterScreen>
    );
  }

  return (
    <div className="p-6 md:p-8">
      <div className="max-w-3xl mx-auto">
        <div className="mb-8">
          <div className="flex justify-between text-sm text-muted mb-2">
            <span>
              Questão {index + 1} / {questoes.length}
            </span>
            <span>{Math.round(progress)}%</span>
          </div>
          <div
            className="h-3 bg-raised rounded-full overflow-hidden"
            role="progressbar"
            aria-valuenow={Math.round(progress)}
            aria-valuemin={0}
            aria-valuemax={100}
            aria-label="Progresso do quiz"
          >
            <div
              className="h-full bg-primary-accent transition-all duration-500"
              style={{ width: `${progress}%` }}
            />
          </div>
        </div>

        <Card>
          <h1 className="text-2xl md:text-3xl font-bold mb-8 leading-snug">
            {questao.enunciado}
          </h1>
          <div
            className="space-y-4"
            role="radiogroup"
            aria-label="Alternativas"
          >
            {questao.alternativas.map((alt) => {
              const active = selected === alt.id;
              return (
                <button
                  key={alt.id}
                  type="button"
                  aria-pressed={active}
                  onClick={() => setSelected(alt.id)}
                  disabled={isSubmitting}
                  className={`w-full text-left p-5 rounded-2xl border transition ${
                    active
                      ? "border-primary-accent bg-primary/10"
                      : "border-line bg-raised hover:border-slate-600"
                  } disabled:opacity-60`}
                >
                  {alt.descricao}
                </button>
              );
            })}
          </div>
          <Button
            onClick={confirmarResposta}
            disabled={!selected}
            loading={isSubmitting}
            fullWidth
            className="mt-8 py-4"
          >
            {isSubmitting ? "Enviando..." : "Confirmar Resposta"}
          </Button>
        </Card>
      </div>
    </div>
  );
}
