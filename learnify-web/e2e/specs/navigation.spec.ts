import { expect, test } from "@playwright/test";
import { mockLearnifyApi } from "../helpers/mock-api";

test.describe("Navegação e fluxo protegido", () => {
  test.beforeEach(async ({ page }) => {
    await mockLearnifyApi(page);
    await page.goto("/login");
    await page.getByLabel("Email").fill("antonio@learnify.dev");
    await page.getByLabel("Senha").fill("123456");
    await page.getByRole("button", { name: "Continuar" }).click();
    await expect(page).toHaveURL(/\/dashboard$/);
  });

  test("navega entre dashboard, ranking e perfil", async ({ page }) => {
    await page.getByRole("link", { name: "Ranking" }).first().click();
    await expect(
      page.getByRole("heading", { name: "Alunos em destaque 🏆" }),
    ).toBeVisible();
    await expect(page).toHaveURL(/\/ranking$/);

    await page.getByRole("link", { name: "Perfil" }).first().click();
    await expect(page.getByRole("heading", { name: "Antonio" })).toBeVisible();
    await expect(page).toHaveURL(/\/perfil$/);
  });

  test("faz logout e volta para login", async ({ page }) => {
    await page.getByRole("button", { name: "Sair" }).click();

    await expect(page).toHaveURL(/\/login$/);
    await expect(page.getByRole("heading", { name: "Entrar" })).toBeVisible();
  });
});
