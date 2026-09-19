export function DonutChart({
  acertos,
  erros,
  size = 140,
  strokeWidth = 22,
}: {
  acertos: number;
  erros: number;
  size?: number;
  strokeWidth?: number;
}) {
  const total = acertos + erros;
  if (total === 0) {
    return (
      <div className="flex items-center justify-center h-full text-faint text-sm">
        Sem dados
      </div>
    );
  }

  const radius = (size - strokeWidth) / 2;
  const circumference = 2 * Math.PI * radius;
  const acertosPct = acertos / total;
  const errosPct = erros / total;
  const acertosDash = circumference * acertosPct;
  const errosDash = circumference * errosPct;

  return (
    <div className="flex items-center gap-6 justify-center">
      <svg width={size} height={size} viewBox={`0 0 ${size} ${size}`}>
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke="#1e293b"
          strokeWidth={strokeWidth}
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke="#10b981"
          strokeWidth={strokeWidth}
          strokeDasharray={`${acertosDash} ${circumference - acertosDash}`}
          strokeDashoffset={0}
          strokeLinecap="round"
          transform={`rotate(-90 ${size / 2} ${size / 2})`}
          className="transition-all duration-1000"
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          fill="none"
          stroke="#f43f5e"
          strokeWidth={strokeWidth}
          strokeDasharray={`${errosDash} ${circumference - errosDash}`}
          strokeDashoffset={-acertosDash}
          strokeLinecap="round"
          transform={`rotate(-90 ${size / 2} ${size / 2})`}
          className="transition-all duration-1000"
        />
        <text
          x="50%"
          y="50%"
          textAnchor="middle"
          dominantBaseline="middle"
          className="fill-white text-lg font-bold"
        >
          {Math.round(acertosPct * 100)}%
        </text>
      </svg>
      <div className="flex flex-col gap-2 text-sm">
        <div className="flex items-center gap-2">
          <span className="w-3 h-3 rounded-full bg-emerald-500" />
          <span>Acertos: {acertos}</span>
        </div>
        <div className="flex items-center gap-2">
          <span className="w-3 h-3 rounded-full bg-rose-500" />
          <span>Erros: {erros}</span>
        </div>
      </div>
    </div>
  );
}
