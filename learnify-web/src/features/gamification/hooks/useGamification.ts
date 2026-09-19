import { useState } from "react";

export function useGamification() {
  const [xp, setXp] = useState(250);
  const [streak, setStreak] = useState(5);

  function addXP(value: number) {
    setXp((prev) => prev + value);
  }

  function increaseStreak() {
    setStreak((prev) => prev + 1);
  }

  return {
    xp,
    streak,
    addXP,
    increaseStreak,
  };
}
