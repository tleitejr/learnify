import { cn } from "../../utils/utils";

export function Card({
  className,
  children,
  ...props
}: React.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      className={cn(
        "bg-surface border border-line rounded-3xl p-6 md:p-8",
        className,
      )}
      {...props}
    >
      {children}
    </div>
  );
}
