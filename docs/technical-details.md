![Learnify](img/banner.png "Learnify")

<details>
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="technical-details.md">English</a></li>
        <li><a href="pt-BR/technical-details.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Technical Details

This page contains implementation details that are useful for development but intentionally omitted from the main README.

### Frontend

- React `19.2.5` and React DOM `19.2.5`.
- TypeScript `6.0.2`.
- Vite `8.0.10` with `@vitejs/plugin-react` `6.0.1`.
- React Router DOM `7.14.2` for routing.
- Axios `1.15.2` for HTTP communication.
- TanStack React Query `5.100.7` and its devtools for asynchronous state and cache.
- Zustand `5.0.12` for client state.
- React Hook Form `7.74.0`, Zod `4.4.1`, and `@hookform/resolvers` `5.2.2` for forms and validation.
- Tailwind CSS `4.2.4` with its Vite plugin for styling.
- Recharts `3.8.1` for charts, Sonner `2.0.7` for notifications, and Lucide React `1.14.0` for icons.
- ESLint `10.2.1` and Prettier `3.9.7` for code quality and formatting.

### Backend

- Java 21 through the Gradle Toolchain.
- Spring Boot `4.0.3`.
- Spring Web MVC, Spring Data JPA, Spring Security, and Spring Boot Actuator.
- JJWT `0.11.5` for JWT support.
- Lombok for boilerplate reduction.
- Logback with `logstash-logback-encoder` `7.4` for structured logs.
- Gradle Wrapper `9.3.1`.

### Database and persistence

- PostgreSQL `17`.
- Spring Data JPA and Hibernate for persistence and object-relational mapping.
- SQL data initialization controlled by `DATA_LOCATIONS`.
- Schema updates configured with `spring.jpa.hibernate.ddl-auto=update` in the main application configuration.

### Testing and quality

- JUnit 5 and H2 for backend unit tests.
- Testcontainers `1.21.4` with PostgreSQL for integration tests.
- JaCoCo `0.8.12` for backend coverage reports and verification.
- Vitest `5.0.0`, Testing Library, jsdom, and MSW for frontend tests.
- Playwright `1.63.0` for Chromium E2E tests.
- ESLint for frontend linting.

### Infrastructure

- Docker and Docker Compose.
- Compose services `db`, `api`, and `web` on a bridge network.
- PostgreSQL persistence through the `data` volume.
- Frontend dependency persistence through the `web_dependencies` volume.
- Database and API health checks used for service startup ordering.

The repository includes a GitHub Actions continuous integration workflow at `.github/workflows/ci.yml`. It automates backend and frontend validation, coverage checks, E2E tests, and report artifacts for pushes and pull requests targeting `main`. No continuous delivery step, such as deployment or release publication, is confirmed in the repository.
