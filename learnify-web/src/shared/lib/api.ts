import axios from "axios";

const apiUrl = (
  import.meta.env.VITE_API_URL || "http://localhost:8080"
).replace(/\/$/, "");

export const api = axios.create({
  baseURL: `${apiUrl}/api/v1`,
  withCredentials: true,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const isAuthRoute = ["/", "/login", "/signup"].includes(
      window.location.pathname,
    );

    if (error.response?.status === 401 || error.response?.status === 403) {
      localStorage.removeItem("token");

      if (!isAuthRoute) {
        window.location.href = "/";
      }
    }

    return Promise.reject(error);
  },
);
