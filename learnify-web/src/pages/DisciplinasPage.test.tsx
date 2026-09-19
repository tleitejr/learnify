import userEvent from "@testing-library/user-event";
import { describe, expect, it } from "vitest";
import { renderWithProviders, screen, waitFor } from "../test/test-utils";
import DisciplinasPage from "./DisciplinasPage";

describe("DisciplinasPage", () => {
  it("shows loading state while request is pending", async () => {
    renderWithProviders(<DisciplinasPage />);
    expect(screen.getByText("Carregando disciplinas...")).toBeInTheDocument();
    expect(await screen.findByText("Matemática")).toBeInTheDocument();
  });

  it("renders disciplines and opens/closes modal", async () => {
    const user = userEvent.setup();
    const { container } = renderWithProviders(<DisciplinasPage />);

    expect(await screen.findByText("Matemática")).toBeInTheDocument();
    await user.click(screen.getByText("Matemática"));

    expect(await screen.findByText("Conteúdos")).toBeInTheDocument();
    expect(document.body.style.overflow).toBe("hidden");

    await user.click(screen.getByText("Conteúdos"));
    expect(screen.getByText("Conteúdos")).toBeInTheDocument();

    const backdrop = container.querySelector(".fixed.inset-0");
    expect(backdrop).toBeTruthy();
    await user.click(backdrop!);
    await waitFor(() => {
      expect(screen.queryByText("Conteúdos")).not.toBeInTheDocument();
    });
    expect(document.body.style.overflow).toBe("auto");
  });

  it("closes modal on Escape key", async () => {
    const user = userEvent.setup();
    renderWithProviders(<DisciplinasPage />);

    await user.click(await screen.findByText("Matemática"));
    expect(await screen.findByText("Conteúdos")).toBeInTheDocument();

    await user.keyboard("{Escape}");
    await waitFor(() => {
      expect(screen.queryByText("Conteúdos")).not.toBeInTheDocument();
    });
  });
});
