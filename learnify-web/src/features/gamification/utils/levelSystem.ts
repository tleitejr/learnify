export function getLevel(xp: number) {
  return Math.floor(xp / 1000);
}

export function getXPProgress(xp: number) {
  return xp % 1000;
}
