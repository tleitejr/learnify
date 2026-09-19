import { NavLink } from "react-router-dom";
import { BookOpen, LayoutDashboard, Trophy, User } from "lucide-react";
import { cn } from "../../utils/utils";

const items = [
  { to: "/dashboard", label: "Dashboard", icon: LayoutDashboard },
  { to: "/ranking", label: "Ranking", icon: Trophy },
  { to: "/aprender", label: "Aprender", icon: BookOpen },
  { to: "/perfil", label: "Perfil", icon: User },
];

export default function MobileNav() {
  return (
    <nav
      aria-label="Navegação principal"
      className="md:hidden fixed bottom-0 inset-x-0 z-50 bg-surface border-t border-line"
    >
      <ul className="flex">
        {items.map(({ to, label, icon: Icon }) => (
          <li key={to} className="flex-1">
            <NavLink
              to={to}
              className={({ isActive }) =>
                cn(
                  "flex flex-col items-center gap-1 py-2.5 text-xs font-medium transition",
                  isActive
                    ? "text-primary-text"
                    : "text-muted hover:text-white",
                )
              }
            >
              <Icon size={20} aria-hidden />
              {label}
            </NavLink>
          </li>
        ))}
      </ul>
    </nav>
  );
}
