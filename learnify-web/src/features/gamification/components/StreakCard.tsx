export default function StreakCard({ days }: { days: number }) {
  return (
    <div className="bg-surface p-6 rounded-2xl border border-line">
      <h2 className="text-xl font-bold mb-2">Sequência 🔥</h2>

      <p className="text-3xl font-bold text-orange-400">{days} dias</p>
    </div>
  );
}
