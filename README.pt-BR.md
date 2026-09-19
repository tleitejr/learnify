![Learnify](docs/img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="README.md">English</a></li>
        <li><a href="README.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>

## Visão geral

O Learnify é uma plataforma educacional gamificada desenvolvida como Trabalho de Conclusão de Curso por **Antonio Carlos Leite Junior**.

O Learnify apoia uma jornada de estudos organizada por disciplinas e conteúdos. Os estudantes podem criar uma conta ou fazer login, escolher uma disciplina, acessar seus conteúdos, responder quizzes e acompanhar sua evolução por meio de pontuação, estatísticas, conquistas e ranking.

A solução combina uma aplicação web em React, uma API REST em Spring Boot e persistência em PostgreSQL. Ela está preparada para ser executada como uma composição de serviços com Docker Compose.

## Principais funcionalidades

- Cadastro, login, logout e exclusão de conta.
- Autenticação baseada em JWT para áreas protegidas.
- Navegação por disciplinas e conteúdos.
- Quizzes e envio de respostas.
- Atualização do progresso e da pontuação após os quizzes.
- Dashboard com estatísticas e conquistas.
- Ranking paginado de usuários.
- Perfil do usuário autenticado.
- Documentação interativa da API.

## Como funciona

1. O estudante cria uma conta ou faz login.
2. O estudante seleciona uma disciplina e abre seus conteúdos.
3. O estudante responde aos quizzes associados ao conteúdo.
4. A API registra o resultado e atualiza os dados de progresso e pontuação.
5. A aplicação apresenta estatísticas, conquistas e informações do ranking.

## Minha contribuição

O repositório registra meu trabalho, como autor do projeto, em toda a solução full stack, incluindo:

- Os fluxos do frontend React para autenticação, disciplinas, conteúdos, quizzes, dashboard, perfil, gamificação e ranking.
- A estrutura da API Spring Boot para autenticação, recursos de domínio, persistência e monitoramento de saúde.
- Testes automatizados unitários, de integração e end-to-end para os fluxos implementados.
- A orquestração local em contêineres para a aplicação web, a API e o banco PostgreSQL.

## Tecnologias

- **Frontend:** React, TypeScript, Vite, React Router, Axios, TanStack React Query, Zustand, React Hook Form, Zod e Tailwind CSS.
- **Backend:** Java, Spring Boot, Spring Web MVC, Spring Data JPA, Spring Security, JJWT e Spring Boot Actuator.
- **Banco de dados:** PostgreSQL com Hibernate por meio do Spring Data JPA.
- **Testes:** Vitest, Testing Library, JUnit 5, H2, Testcontainers, JaCoCo e Playwright.
- **Infraestrutura:** Docker e Docker Compose.

## Início rápido

Na raiz do repositório, crie os arquivos de ambiente locais e inicie a solução completa com:

```bash
./scripts/setup-local.sh
docker compose up --build
```

O script copia os modelos `.env.example` rastreados pelo Git para arquivos `.env` locais e gera um segredo JWT local. Esses arquivos gerados são ignorados pelo Git e não devem ser publicados.

Acesse a aplicação web em `http://localhost:5173`. A API estará disponível em `http://localhost:8080`.

## Documentação

- [Arquitetura](docs/pt-BR/architecture.pt-BR.md)
- [Configuração](docs/pt-BR/setup.pt-BR.md)
- [Variáveis de ambiente](docs/pt-BR/environment-variables.pt-BR.md)
- [Testes](docs/pt-BR/testing.pt-BR.md)
- [API](docs/pt-BR/api.pt-BR.md)
- [Detalhes técnicos](docs/pt-BR/technical-details.pt-BR.md)

## Melhorias futuras

O repositório já possui integração contínua por meio do GitHub Actions para builds, testes, lint, verificações de cobertura e validação E2E automatizados. Estender esse fluxo com uma etapa confirmada de entrega contínua, como implantação ou publicação de releases, é uma possível melhoria.

## Licença

Consulte o arquivo [LICENSE](LICENSE) para os termos de uso do projeto.
