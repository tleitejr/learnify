import { useQuery } from "@tanstack/react-query";
import { BookOpen, Trophy, Target } from "lucide-react";
import { useState } from "react";
import { getDisciplinas } from "../features/disciplina/api/getDisciplinas";
import type { Disciplina } from "../types/models";
import ConteudosPage from "./ConteudosPage";
import { InfoCard } from "../shared/components/ui/InfoCard";
import { Modal } from "../shared/components/ui/Modal";
import { LoadingState } from "../shared/components/ui/LoadingState";

export default function DisciplinasPage() {
  const [selectedDisciplinaId, setSelectedDisciplinaId] = useState<
    string | null
  >(null);

  const { data, isLoading } = useQuery({
    queryKey: ["disciplinas"],
    queryFn: getDisciplinas,
  });

  const handleCloseModal = () => setSelectedDisciplinaId(null);

  if (isLoading) {
    return <LoadingState label="Carregando disciplinas..." />;
  }

  return (
    <div className="max-w-7xl mx-auto p-6 md:p-8">
      <section className="mb-12 bg-linear-to-r from-violet-600 to-indigo-600 rounded-3xl p-8 md:p-12">
        <h1 className="text-4xl md:text-6xl font-bold leading-tight">
          Estude com foco,
          <br />
          Evolua jogando.
        </h1>
        <p className="mt-4 text-white/80 max-w-2xl text-lg">
          Aprenda conteúdos do ensino médio com quizzes, desafios e progresso
          inteligente.
        </p>
      </section>

      <section className="grid md:grid-cols-3 gap-4 mb-10">
        <InfoCard icon={<BookOpen />} title="Conteúdos organizados" />
        <InfoCard icon={<Target />} title="Atividades práticas" />
        <InfoCard icon={<Trophy />} title="Ranking e conquistas" />
      </section>

      <div className="mb-6">
        <h2 className="text-3xl font-bold">Escolha uma disciplina</h2>
        <p className="text-muted mt-2">Continue sua jornada de aprendizado.</p>
      </div>

      <section className="grid md:grid-cols-2 xl:grid-cols-3 gap-6">
        {data?.map((item: Disciplina) => (
          <button
            key={item.id}
            onClick={() => setSelectedDisciplinaId(item.id)}
            className="bg-surface border border-line rounded-3xl p-6 hover:border-primary-accent hover:-translate-y-1 transition-all flex flex-col items-center justify-center text-center min-h-[180px]"
          >
            <h3 className="text-2xl font-bold m-0">{item.titulo}</h3>
          </button>
        ))}
      </section>

      <Modal
        open={!!selectedDisciplinaId}
        onClose={handleCloseModal}
        title="Conteúdos da disciplina"
        className="items-start"
        panelClassName="bg-base border-slate-700/50 my-8 max-w-6xl max-h-[85vh] overflow-y-auto"
      >
        {selectedDisciplinaId && (
          <ConteudosPage disciplinaId={selectedDisciplinaId} isModal={true} />
        )}
      </Modal>
    </div>
  );
}
