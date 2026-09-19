import { useEffect, useState } from "react";

export default function RewardPopup({ xp }: { xp: number }) {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    setTimeout(() => setVisible(false), 2000);
  }, []);

  if (!visible) return null;

  return (
    <div className="fixed top-10 right-10 bg-primary px-6 py-4 rounded-2xl shadow-2xl animate-bounce">
      +{xp} XP 🎉
    </div>
  );
}
