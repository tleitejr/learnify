import { Mail, User, Code2, Layers, Container, TrendingUp } from "lucide-react";

export default function CreditosPage() {
  return (
    <div className="min-h-screen bg-slate-950 text-white">
      <div className="max-w-7xl mx-auto p-6 md:p-8">
        <section className="mb-10 bg-linear-to-r from-violet-600 to-indigo-600 rounded-3xl p-8 md:p-10">
          <h1 className="text-4xl md:text-5xl font-bold leading-tight">
            Créditos
          </h1>
          <p className="mt-2 text-white/80 max-w-2xl text-lg">
            Trabalho de Conclusão de Curso – Engenharia de Software | UNINTER
          </p>
        </section>

        <div>
          <div className="mb-10 bg-slate-900 border border-slate-800 rounded-3xl p-8 md:p-10">
            <div className="flex items-center justify-center gap-3 mb-4">
              <User className="text-violet-400" size={24} />
              <h2 className="text-2xl font-bold">Desenvolvedor</h2>
            </div>
            <div className="flex flex-col items-center text-center space-y-2">
              <p className="text-xl font-semibold">
                Antonio Carlos Leite Junior
              </p>
              <p className="text-slate-400">
                Graduando em Engenharia de Software
              </p>
              <div className="flex items-center justify-center gap-2 text-slate-400">
                <Mail size={16} />
                <a
                  href="mailto:tleitejr@proton.me"
                  className="hover:text-violet-400 transition-colors"
                >
                  tleitejr@proton.me
                </a>
              </div>
            </div>
          </div>
        </div>

        <div className="space-y-6">
          <h2 className="text-3xl font-bold">⚙️ Stack</h2>

          <div className="grid md:grid-cols-2 gap-6">
            <div className="bg-slate-900 border border-slate-800 rounded-3xl p-6">
              <div className="flex items-center gap-3 mb-4">
                <Code2 className="text-sky-400" size={22} />
                <h3 className="text-xl font-semibold">Frontend</h3>
              </div>
              <div className="flex flex-wrap gap-2">
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  React 19
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Vite 8
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  TypeScript 6
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Tailwind 4
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  React Router 7
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  TanStack Query
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Zustand
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  React Hook Form
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Zod
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Axios
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Recharts
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Lucide React
                </span>
                <span className="bg-sky-500/10 text-sky-400 border border-sky-500/20 rounded-full px-3 py-1 text-xs">
                  Sonner
                </span>
              </div>
            </div>

            <div className="bg-slate-900 border border-slate-800 rounded-3xl p-6">
              <div className="flex items-center gap-3 mb-4">
                <Layers className="text-green-400" size={22} />
                <h3 className="text-xl font-semibold">Backend</h3>
              </div>
              <div className="flex flex-wrap gap-2">
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Java 21
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Spring Boot 4
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Spring Security
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Spring Data JPA
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Spring Actuator
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  JWT (JJWT)
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Lombok
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Logstash Encoder
                </span>
                <span className="bg-green-500/10 text-green-400 border border-green-500/20 rounded-full px-3 py-1 text-xs">
                  Gradle
                </span>
              </div>
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6">
            <div className="bg-slate-900 border border-slate-800 rounded-3xl p-6">
              <div className="flex items-center gap-3 mb-4">
                <Container className="text-indigo-400" size={22} />
                <h3 className="text-xl font-semibold">
                  Infraestrutura & Banco
                </h3>
              </div>
              <div className="flex flex-wrap gap-2">
                <span className="bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 rounded-full px-3 py-1 text-xs">
                  PostgreSQL
                </span>
                <span className="bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 rounded-full px-3 py-1 text-xs">
                  Docker
                </span>
                <span className="bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 rounded-full px-3 py-1 text-xs">
                  Docker Compose
                </span>
                <span className="bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 rounded-full px-3 py-1 text-xs">
                  Containerização Full-Stack
                </span>
              </div>
            </div>

            <div className="bg-slate-900 border border-slate-800 rounded-3xl p-6">
              <div className="flex items-center gap-3 mb-4">
                <TrendingUp className="text-amber-400" size={22} />
                <h3 className="text-xl font-semibold">Qualidade & Testes</h3>
              </div>
              <div className="flex flex-wrap gap-2">
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  Vitest
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  Testing Library
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  MSW
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  JUnit
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  H2 Database
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  JaCoCo
                </span>
                <span className="bg-amber-500/10 text-amber-400 border border-amber-500/20 rounded-full px-3 py-1 text-xs">
                  ESLint
                </span>
              </div>
            </div>
          </div>
        </div>

        <footer className="mt-12 pt-6 border-t border-slate-800 text-center text-slate-500 text-sm">
          &copy; {new Date().getFullYear()} Learnify. Desenvolvido por{" "}
          <a
            href="https://github.com/tleitejr"
            target="_blank"
            rel="noopener noreferrer"
            className="text-violet-400 hover:underline"
          >
            Antonio C. Leite Jr
          </a>
        </footer>
      </div>
    </div>
  );
}
