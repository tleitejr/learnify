export default function AuthLayout({
  title,
  children,
}: {
  title: string;
  children: React.ReactNode;
}) {
  return (
    <main className="min-h-screen bg-base relative">
      <div className="absolute top-0 left-0 p-6">
        <img
          src="/Learnify.png"
          alt="Learnify"
          className="h-8 md:h-10 w-auto object-contain"
        />
      </div>

      <div className="min-h-screen flex items-center justify-center px-4 py-20">
        <div className="w-full max-w-md bg-surface rounded-3xl p-6 md:p-8 border border-line shadow-2xl">
          <h1 className="text-3xl font-bold mb-6">{title}</h1>
          {children}
        </div>
      </div>
    </main>
  );
}
