import { getLevel } from "../utils/levelSystem";

export default function LevelBadge({ xp }: { xp: number }) {
  const level = getLevel(xp);

  return (
    <div className="bg-primary px-4 py-2 rounded-xl font-bold shadow-lg">
      Level {level}
    </div>
  );
}
