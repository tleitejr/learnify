export default function XPBar({ xp }: { xp: number }) {
  const progress = xp % 1000;

  return (
    <div className="bg-raised rounded-xl p-4">
      <div className="flex justify-between mb-2 text-sm text-muted">
        <span>XP</span>
        <span>{progress}/1000</span>
      </div>

      <div className="w-full h-3 bg-slate-700 rounded-full overflow-hidden">
        <div
          className="h-full bg-primary-accent transition-all duration-700"
          style={{ width: `${(progress / 1000) * 100}%` }}
        />
      </div>
    </div>
  );
}
