import { Skeleton } from "./Skeleton";

export function StatCard({
  label,
  value,
  icon,
  loading,
}: {
  label: string;
  value: string | number;
  icon: React.ReactNode;
  loading: boolean;
}) {
  return (
    <div className="bg-surface border border-line rounded-2xl p-5 hover:border-primary-accent transition">
      <div className="flex items-center justify-between mb-3">
        <span className="text-muted text-sm">{label}</span>
        <span className="p-2 bg-raised rounded-lg">{icon}</span>
      </div>
      {loading ? (
        <Skeleton className="h-8 w-20" />
      ) : (
        <p className="text-2xl font-bold">{value}</p>
      )}
    </div>
  );
}
