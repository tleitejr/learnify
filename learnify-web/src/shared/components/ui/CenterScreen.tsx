export function CenterScreen({ children }: { children: React.ReactNode }) {
  return (
    <div className="min-h-screen bg-base text-white flex items-center justify-center px-4">
      {children}
    </div>
  );
}
