import { createBrowserRouter, Navigate } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import DashboardPage from "../pages/DashboardPage";
import ProtectedRoute from "../shared/routes/ProtectedRoute";
import PublicRoute from "../shared/routes/PublicRoute";
import AppLayout from "../shared/components/layout/AppLayout";

import ConteudosPage from "../pages/ConteudosPage";
import DisciplinasPage from "../pages/DisciplinasPage";
import QuizPage from "../pages/QuizPage";
import SignupPage from "../pages/SignupPage";
import RankingPage from "../pages/RankingPage";
import PerfilPage from "../pages/PerfilPage";
import CreditosPage from "../pages/CreditosPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/login" replace />,
  },
  {
    path: "/login",
    element: (
      <PublicRoute>
        <LoginPage />
      </PublicRoute>
    ),
  },
  {
    path: "/signup",
    element: (
      <PublicRoute>
        <SignupPage />
      </PublicRoute>
    ),
  },
  {
    element: (
      <ProtectedRoute>
        <AppLayout />
      </ProtectedRoute>
    ),
    children: [
      {
        path: "/dashboard",
        element: <DashboardPage />,
      },
      {
        path: "/disciplinas/:disciplinaId/conteudos",
        element: <ConteudosPage />,
      },
      {
        path: "/aprender",
        element: <DisciplinasPage />,
      },
      {
        path: "/conteudos/:conteudoId/quiz",
        element: <QuizPage />,
      },
      {
        path: "/ranking",
        element: <RankingPage />,
      },
      {
        path: "/perfil",
        element: <PerfilPage />,
      },
      {
        path: "/creditos",
        element: <CreditosPage />,
      },
    ],
  },
]);
