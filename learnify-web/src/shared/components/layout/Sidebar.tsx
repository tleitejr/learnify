import { useState, useEffect } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { logoutRequest } from "../../../features/auth/api/logout";
import { useAuthStore } from "../../../features/auth/authStore";
import { cn } from "../../utils/utils";
import {
  BookOpen,
  LayoutDashboard,
  LogOut,
  Trophy,
  User,
  ChevronLeft,
  ChevronRight,
  Braces,
  Copyright,
} from "lucide-react";
import { apiDocsUrl } from "../../../features/docs/docs";

export default function Sidebar() {
  const navigate = useNavigate();
  const logout = useAuthStore((state) => state.logout);
  const [loading, setLoading] = useState(false);

  const [collapsed, setCollapsed] = useState(() => {
    const saved = localStorage.getItem("sidebar-collapsed");
    return saved ? JSON.parse(saved) : false;
  });

  useEffect(() => {
    localStorage.setItem("sidebar-collapsed", JSON.stringify(collapsed));
  }, [collapsed]);

  async function handleLogout() {
    try {
      setLoading(true);
      await logoutRequest();
      logout();
      navigate("/login", { replace: true });
    } catch {
      logout();
      navigate("/login", { replace: true });
    } finally {
      setLoading(false);
    }
  }

  return (
    <aside
      className={cn(
        "sticky top-0 h-screen shrink-0 hidden md:flex flex-col border-r border-line bg-surface transition-all duration-300 z-40",
        collapsed ? "w-18 px-2 py-6" : "w-72 p-6",
      )}
    >
      <button
        onClick={() => setCollapsed(!collapsed)}
        className="absolute -right-3 top-8 w-6 h-6 rounded-full bg-primary text-white flex items-center justify-center shadow-lg hover:bg-primary-hover transition z-10"
        aria-label={collapsed ? "Expandir menu" : "Minimizar menu"}
        aria-expanded={!collapsed}
      >
        {collapsed ? <ChevronRight size={14} /> : <ChevronLeft size={14} />}
      </button>

      <div
        className={`w-full shrink-0 transition-all duration-300 ${
          collapsed ? "mb-6 flex justify-center" : "mb-10"
        }`}
      >
        {collapsed ? (
          <img
            src="/favicon.svg"
            alt="Learnify"
            className="block h-8 w-8 object-contain"
          />
        ) : (
          <img
            src="/Learnify.png"
            alt="Learnify"
            className="h-8 md:h-10 w-auto object-contain"
          />
        )}
      </div>

      <nav
        aria-label="Navegação principal"
        className="space-y-3 flex-1 overflow-y-auto"
      >
        <MenuLink
          to="/dashboard"
          icon={<LayoutDashboard size={20} />}
          text="Dashboard"
          collapsed={collapsed}
        />
        <MenuLink
          to="/ranking"
          icon={<Trophy size={20} />}
          text="Ranking"
          collapsed={collapsed}
        />
        <MenuLink
          to="/aprender"
          icon={<BookOpen size={20} />}
          text="Aprender"
          collapsed={collapsed}
        />
        <MenuLink
          to="/perfil"
          icon={<User size={20} />}
          text="Perfil"
          collapsed={collapsed}
        />
      </nav>

      <nav aria-label="Ações" className="shrink-0 space-y-3 mt-3">
        <a
          href={apiDocsUrl}
          target="_blank"
          rel="noreferrer"
          className="block w-full"
        >
          <Menu
            icon={<Braces size={20} />}
            text="API Docs"
            collapsed={collapsed}
          />
        </a>
        <Link to="/creditos">
          <Menu
            icon={<Copyright size={20} />}
            text="Créditos"
            collapsed={collapsed}
          />
        </Link>
        <button
          onClick={handleLogout}
          disabled={loading}
          className="w-full shrink-0 mt-3"
        >
          <Menu
            icon={<LogOut size={20} />}
            text={loading ? "Saindo..." : "Sair"}
            className="text-danger-text hover:bg-danger/10"
            collapsed={collapsed}
          />
        </button>
      </nav>
    </aside>
  );
}

function MenuLink({
  to,
  icon,
  text,
  collapsed,
}: {
  to: string;
  icon: React.ReactNode;
  text: string;
  collapsed: boolean;
}) {
  return (
    <NavLink to={to} className="block">
      {({ isActive }) => (
        <Menu icon={icon} text={text} collapsed={collapsed} active={isActive} />
      )}
    </NavLink>
  );
}

function Menu({
  icon,
  text,
  className = "",
  collapsed,
  active = false,
}: {
  icon: React.ReactNode;
  text: string;
  className?: string;
  collapsed: boolean;
  active?: boolean;
}) {
  return (
    <div
      className={cn(
        "flex items-center gap-3 rounded-xl cursor-pointer transition",
        collapsed ? "justify-center px-2 py-3 w-full" : "px-4 py-3",
        active
          ? "bg-primary/15 text-primary-text font-semibold"
          : "text-slate-300 hover:bg-raised",
        className,
      )}
      title={collapsed ? text : undefined}
    >
      {icon}
      {!collapsed && <span>{text}</span>}
    </div>
  );
}
