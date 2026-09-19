import type { Page } from "@playwright/test";

export async function mockLearnifyApi(page: Page) {
  const user = {
    id: "u-1",
    nome: "Antonio",
    email: "antonio@learnify.dev",
    nivel: 3,
    pontuacaoTotal: 2450,
  };

  const stats = {
    desempenho: "88%",
    acertos: 42,
    erros: 8,
    mediaPontos: 76,
    nivel: 3,
    pontuacaoTotal: 2450,
  };

  const conquistas = [
    {
      id: "c-1",
      titulo: "Primeiro passo",
      descricao: "Concluiu a primeira etapa.",
    },
    {
      id: "c-2",
      titulo: "Constância",
      descricao: "Manteve sequência de estudos.",
    },
  ];

  const ranking = {
    content: [
      { id: "u-1", nomeUsuario: "Antonio", pontuacaoTotal: 2450, nivel: 3 },
      { id: "u-2", nomeUsuario: "Bia", pontuacaoTotal: 2200, nivel: 3 },
      { id: "u-3", nomeUsuario: "Caio", pontuacaoTotal: 1950, nivel: 2 },
    ],
    pageable: { pageNumber: 0, pageSize: 3 },
    totalElements: 3,
  };

  const disciplinas = [
    {
      id: "matematica",
      titulo: "Matemática",
      descricao: "Álgebra e Estatística",
    },
    { id: "fisica", titulo: "Física", descricao: "Mecânica e Energia" },
  ];

  await page.route("**/api/v1/auth/login", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify({ token: "token-123" }),
    });
  });

  await page.route("**/api/v1/auth/logout", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify({ message: "Logout realizado com sucesso." }),
    });
  });

  await page.route("**/api/v1/usuarios/me", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(user),
    });
  });

  await page.route("**/api/v1/dashboard/stats", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(stats),
    });
  });

  await page.route("**/api/v1/dashboard/conquistas", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(conquistas),
    });
  });

  await page.route("**/api/v1/ranking", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(ranking),
    });
  });

  await page.route("**/api/v1/disciplinas", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify(disciplinas),
    });
  });

  await page.route("**/api/v1/conteudos/**", async (route) => {
    await route.fulfill({
      status: 200,
      contentType: "application/json",
      body: JSON.stringify([]),
    });
  });
}
