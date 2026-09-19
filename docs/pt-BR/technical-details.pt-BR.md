![Learnify](../img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="../technical-details.md">English</a></li>
        <li><a href="technical-details.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Detalhes Técnicos

Esta página contém detalhes de implementação que são úteis para desenvolvimento, mas intencionalmente omitidos do README principal.

### Frontend

- React `19.2.5` e React DOM `19.2.5`.
- TypeScript `6.0.2`.
- Vite `8.0.10` com `@vitejs/plugin-react` `6.0.1`.
- React Router DOM `7.14.2` para roteamento.
- Axios `1.15.2` para comunicação HTTP.
- TanStack React Query `5.100.7` e suas devtools para estado assíncrono e cache.
- Zustand `5.0.12` para estado do cliente.
- React Hook Form `7.74.0`, Zod `4.4.1` e `@hookform/resolvers` `5.2.2` para formulários e validação.
- Tailwind CSS `4.2.4` com seu plugin Vite para estilização.
- Recharts `3.8.1` para gráficos, Sonner `2.0.7` para notificações e Lucide React `1.14.0` para ícones.
- ESLint `10.2.1` e Prettier `3.9.7` para qualidade de código e formatação.

### Backend

- Java 21 por meio do Gradle Toolchain.
- Spring Boot `4.0.3`.
- Spring Web MVC, Spring Data JPA, Spring Security e Spring Boot Actuator.
- JJWT `0.11.5` para suporte a JWT.
- Lombok para redução de boilerplate.
- Logback com `logstash-logback-encoder` `7.4` para logs estruturados.
- Gradle Wrapper `9.3.1`.

### Banco de dados e persistência

- PostgreSQL `17`.
- Spring Data JPA e Hibernate para persistência e mapeamento objeto-relacional.
- Inicialização de dados SQL controlada por `DATA_LOCATIONS`.
- Atualizações de esquema configuradas com `spring.jpa.hibernate.ddl-auto=update` na configuração principal da aplicação.

### Testes e qualidade

- JUnit 5 e H2 para testes unitários do backend.
- Testcontainers `1.21.4` com PostgreSQL para testes de integração.
- JaCoCo `0.8.12` para relatórios e verificação de cobertura do backend.
- Vitest `5.0.0`, Testing Library, jsdom e MSW para testes do frontend.
- Playwright `1.63.0` para testes E2E no Chromium.
- ESLint para linting do frontend.

### Infraestrutura

- Docker e Docker Compose.
- Serviços Compose `db`, `api` e `web` em uma rede bridge.
- Persistência do PostgreSQL por meio do volume `data`.
- Persistência de dependências do frontend por meio do volume `web_dependencies`.
- Verificações de integridade do banco de dados e da API usadas para ordenação de inicialização dos serviços.

O repositório inclui um fluxo de trabalho de integração contínua do GitHub Actions em `.github/workflows/ci.yml`. Ele automatiza validação de backend e frontend, verificações de cobertura, testes E2E e artefatos de relatórios para pushes e pull requests direcionados à `main`. Nenhuma etapa de entrega contínua, como implantação ou publicação de release, está confirmada no repositório.
