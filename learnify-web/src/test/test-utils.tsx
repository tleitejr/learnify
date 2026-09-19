import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import {
  render,
  screen,
  waitFor,
  type RenderOptions,
} from "@testing-library/react";
import type { ReactElement, ReactNode } from "react";
import { MemoryRouter } from "react-router-dom";

export { screen, waitFor };

function createTestQueryClient() {
  return new QueryClient({
    defaultOptions: {
      queries: {
        retry: false,
      },
    },
  });
}

type ExtendedOptions = Omit<RenderOptions, "wrapper"> & {
  route?: string;
  initialEntries?: string[];
  initialIndex?: number;
};

export function renderWithProviders(
  ui: ReactElement,
  {
    route = "/",
    initialEntries,
    initialIndex,
    ...options
  }: ExtendedOptions = {},
) {
  const queryClient = createTestQueryClient();

  function Wrapper({ children }: { children: ReactNode }) {
    return (
      <QueryClientProvider client={queryClient}>
        <MemoryRouter
          initialEntries={initialEntries ?? [route]}
          initialIndex={initialIndex}
        >
          {children}
        </MemoryRouter>
      </QueryClientProvider>
    );
  }

  return {
    queryClient,
    ...render(ui, { wrapper: Wrapper, ...options }),
  };
}
