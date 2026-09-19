import { expect, test } from "@playwright/test";
import { mockLearnifyApi } from "../helpers/mock-api";

test.describe("Autenticação", () => {
  test.beforeEach(async ({ page }) => {
    await mockLearnifyApi(page);
  });

  test("redireciona usuários não autenticados para /login", async ({
    page,
  }) => {
    await page.goto("/dashboard");

    await expect(page).toHaveURL(/\/login$/);
    await expect(page.getByRole("heading", { name: "Entrar" })).toBeVisible();
  });

  test("realiza login com sucesso e acessa o dashboard", async ({ page }) => {
    await page.goto("/login");

    await page.getByLabel("Email").fill("antonio@learnify.dev");
    await page.getByLabel("Senha").fill("123456");
    await page.getByRole("button", { name: "Continuar" }).click();

    await expect(
      page.getByRole("heading", { name: /Olá, Antonio/i }),
    ).toBeVisible();
    await expect(page).toHaveURL(/\/dashboard$/);
    await expect(page.getByRole("link", { name: "Dashboard" })).toHaveAttribute(
      "aria-current",
      "page",
    );
  });

  test("exibe erro ao falhar no login", async ({ page }) => {
    await page.route("**/api/v1/auth/login", async (route) => {
      await route.fulfill({
        status: 401,
        contentType: "application/json",
        body: JSON.stringify({ message: "Unauthorized" }),
      });
    });

    await page.goto("/login");
    await page.getByLabel("Email").fill("antonio@learnify.dev");
    await page.getByLabel("Senha").fill("123456");
    await page.getByRole("button", { name: "Continuar" }).click();

    await expect(page.getByText("Email ou senha inválidos!")).toBeVisible();
  });
});
