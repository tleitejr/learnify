import { useEffect, useRef } from "react";
import { X } from "lucide-react";
import { cn } from "../../utils/utils";

export function Modal({
  open,
  onClose,
  title,
  children,
  className,
  panelClassName,
}: {
  open: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
  className?: string;
  panelClassName?: string;
}) {
  const panelRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!open) return;

    const handleEsc = (e: KeyboardEvent) => {
      if (e.key === "Escape") onClose();
    };
    window.addEventListener("keydown", handleEsc);
    document.body.style.overflow = "hidden";
    panelRef.current?.focus();

    return () => {
      window.removeEventListener("keydown", handleEsc);
      document.body.style.overflow = "auto";
    };
  }, [open, onClose]);

  if (!open) return null;

  return (
    <div
      className={cn(
        "fixed inset-0 z-50 flex items-start justify-center bg-black/80 backdrop-blur-md transition-all duration-300",
        className,
      )}
      onClick={onClose}
    >
      <div
        ref={panelRef}
        role="dialog"
        aria-modal="true"
        aria-label={title}
        tabIndex={-1}
        className={cn(
          "relative bg-slate-950 rounded-2xl shadow-2xl border border-slate-700/50 my-8 mx-4 w-full max-w-6xl outline-none",
          "animate-in fade-in zoom-in duration-300",
          panelClassName,
        )}
        onClick={(e) => e.stopPropagation()}
      >
        <button
          onClick={onClose}
          aria-label="Fechar"
          className="absolute top-3 right-3 z-20 p-2 rounded-full bg-slate-800/90 text-slate-400 hover:text-white transition-colors duration-200 shadow-lg backdrop-blur-sm border border-slate-600/50"
        >
          <X size={20} />
        </button>

        <div className="max-h-[85vh] overflow-y-auto scrollbar-thin scrollbar-thumb-slate-700 scrollbar-track-slate-900">
          {children}
        </div>
      </div>
    </div>
  );
}
