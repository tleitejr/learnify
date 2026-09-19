import { Loader2 } from "lucide-react";

export function LoadingState({ label = "Carregando..." }: { label?: string }) {
  return (
    <div
      role="status"
      className="flex-1 min-h-[40vh] flex flex-col items-center justify-center gap-3 text-muted"
    >
      <Loader2
        size={28}
        className="animate-spin text-primary-text"
        aria-hidden
      />
      <span>{label}</span>
    </div>
  );
}
