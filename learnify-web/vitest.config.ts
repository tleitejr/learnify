import { defineConfig } from "vitest/config";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  test: {
    environment: "jsdom",
    globals: true,
    css: true,
    exclude: ["e2e/**", "**/*.spec.ts", "dist/**", "node_modules/**"],
    setupFiles: ["./src/test/setupTests.ts"],
    coverage: {
      provider: "v8",
      reporter: ["text", "html", "json-summary"],
      include: ["src/**/*.{ts,tsx}"],
      exclude: [
        "src/main.tsx",
        "src/types/**",
        "src/test/**",
        "**/*.d.ts",
        "**/vite.config.*",
        "**/vitest.config.*",
        "**/eslint.config.*",
        "**/tailwind.config.*",
        "dist/**",
        "node_modules/**",
      ],
      thresholds: {
        statements: 90,
        branches: 90,
        functions: 90,
        lines: 90,
      },
    },
  },
});
