import { authHandlers } from "./auth.handlers";
import { contentsHandlers } from "./contents.handlers";
import { dashboardHandlers } from "./dashboard.handlers";
import { disciplinasHandlers } from "./disciplinas.handlers";
import { profileHandlers } from "./profile.handlers";
import { quizHandlers } from "./quiz.handlers";
import { rankingHandlers } from "./ranking.handlers";

export const handlers = [
  ...authHandlers,
  ...profileHandlers,
  ...dashboardHandlers,
  ...rankingHandlers,
  ...disciplinasHandlers,
  ...contentsHandlers,
  ...quizHandlers,
];
