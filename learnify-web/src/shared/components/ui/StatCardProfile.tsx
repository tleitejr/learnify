export function StatCardProfile({
  icon,
  title,
  value,
}: {
  icon: React.ReactNode;
  title: string;
  value: string | number | undefined;
}) {
  return (
    <div className="bg-surface border border-line rounded-3xl p-6">
      <div className="text-primary-text mb-4">{icon}</div>
      <p className="text-muted text-sm">{title}</p>
      <h3 className="text-3xl font-bold mt-2">{value ?? "—"}</h3>
    </div>
  );
}
