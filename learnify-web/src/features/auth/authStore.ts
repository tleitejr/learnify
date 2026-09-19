import { create } from "zustand";

type User = {
  id: number;
  nome: string;
  email: string;
  nivel: number;
  pontuacaoTotal: number;
};

type AuthState = {
  token: string | null;
  user: User | null;

  setToken: (token: string) => void;
  setUser: (user: User) => void;
  logout: () => void;
};

const getStoredToken = () => {
  if (typeof window === "undefined") return null;
  return localStorage.getItem("token");
};

export const useAuthStore = create<AuthState>((set) => ({
  token: getStoredToken(),
  user: null,

  setToken: (token) => {
    localStorage.setItem("token", token);
    set({ token });
  },

  setUser: (user) => set({ user }),

  logout: () => {
    localStorage.removeItem("token");
    set({
      token: null,
      user: null,
    });
  },
}));
