import { useId } from "react";
import { cn } from "../../utils/utils";

export function Input({
  label,
  error,
  id,
  className,
  ...props
}: React.InputHTMLAttributes<HTMLInputElement> & {
  label: string;
  error?: string;
}) {
  const autoId = useId();
  const inputId = id ?? autoId;

  return (
    <div className="w-full">
      <label
        htmlFor={inputId}
        className="block text-sm font-medium text-muted mb-1.5"
      >
        {label}
      </label>
      <input
        id={inputId}
        aria-invalid={!!error || undefined}
        aria-describedby={error ? `${inputId}-error` : undefined}
        className={cn(
          "w-full px-4 py-3 rounded-xl bg-raised text-white border transition",
          "focus:outline-none focus:ring-2 focus:ring-primary-accent focus:border-transparent",
          error ? "border-danger" : "border-slate-700",
          className,
        )}
        {...props}
      />
      {error && (
        <p
          id={`${inputId}-error`}
          role="alert"
          className="text-danger-text text-sm mt-2"
        >
          {error}
        </p>
      )}
    </div>
  );
}
