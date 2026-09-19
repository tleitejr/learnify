import { Navigate } from "react-router-dom";
import { useAuthStore } from "../../features/auth/authStore";

export default function PublicRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const token = useAuthStore((state) => state.token);

  if (token) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}
