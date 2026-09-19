export function InfoCard({
  icon,
  title,
}: {
  icon: React.ReactNode;
  title: string;
}) {
  return (
    <div className="bg-surface border border-line rounded-2xl p-5 flex items-center gap-4">
      <div className="text-primary-text">{icon}</div>
      <span className="font-medium">{title}</span>
    </div>
  );
}
