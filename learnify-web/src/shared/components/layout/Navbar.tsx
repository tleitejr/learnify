export default function Navbar() {
  return (
    <header className="flex items-center justify-between gap-4 rounded-2xl bg-surface p-4 border border-line">
      <input
        aria-label="Buscar conteúdos"
        placeholder="Buscar conteúdos..."
        className="w-full rounded-xl border border-slate-700 bg-raised px-3 py-2 text-sm text-white placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-primary-accent"
      />
      <div className="rounded-xl bg-primary/10 px-3 py-2 text-sm font-semibold text-primary-text">
        250 XP
      </div>
    </header>
  );
}
