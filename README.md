![Learnify](docs/img/banner.png "Learnify")

<details> 
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="README.md">English</a></li>
        <li><a href="README.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>

## Overview

Learnify is a gamified educational platform developed as a final-year academic project by **Antonio Carlos Leite Junior**.

Learnify supports a study journey organized around subjects and learning content. Students can create an account or log in, choose a subject, access its content, answer quizzes, and follow their learning progress through scores, statistics, achievements, and a ranking.

The solution combines a React web application, a Spring Boot REST API, and PostgreSQL persistence. It is prepared to run as a multi-service stack with Docker Compose.

## Main Features

- Account registration, login, logout, and account deletion.
- JWT-based authentication for protected areas.
- Subject and content browsing.
- Quizzes and answer submission.
- Progress and score updates after quizzes.
- Dashboard with statistics and achievements.
- Paginated user ranking.
- Authenticated user profile.
- Interactive API documentation.

## How It Works

1. A student creates an account or signs in.
2. The student selects a subject and opens its content.
3. The student answers quizzes associated with that content.
4. The API records the result and updates progress and score data.
5. The application presents statistics, achievements, and ranking information.

## My Contribution

The repository records my work as the project author across the full-stack solution, including:

- The React frontend flows for authentication, subjects, content, quizzes, dashboard, profile, gamification, and ranking.
- The Spring Boot API structure for authentication, domain resources, persistence, and health monitoring.
- Automated unit, integration, and end-to-end test coverage for the implemented application flows.
- Local container orchestration for the web application, API, and PostgreSQL database.

## Technologies

- **Frontend:** React, TypeScript, Vite, React Router, Axios, TanStack React Query, Zustand, React Hook Form, Zod, and Tailwind CSS.
- **Backend:** Java, Spring Boot, Spring Web MVC, Spring Data JPA, Spring Security, JJWT, and Spring Boot Actuator.
- **Database:** PostgreSQL with Hibernate through Spring Data JPA.
- **Testing:** Vitest, Testing Library, JUnit 5, H2, Testcontainers, JaCoCo, and Playwright.
- **Infrastructure:** Docker and Docker Compose.

## Quick Start

From the repository root, create the local environment files and start the complete stack with:

```bash
./scripts/setup-local.sh
docker compose up --build
```

The setup script copies the tracked `.env.example` templates into local `.env` files and generates a local JWT secret. These generated files are ignored by Git and must not be committed.

Open the web application at `http://localhost:5173`. The API is available at `http://localhost:8080`.

## Documentation

- **[Architecture](docs/architecture.md)**
- **[Setup](docs/setup.md)**
- **[Environment Variables](docs/environment-variables.md)**
- **[Testing](docs/testing.md)**
- **[API](docs/api.md)**
- **[Technical Details](docs/technical-details.md)**

## Future Improvements

The repository already includes continuous integration through GitHub Actions for automated builds, tests, linting, coverage checks, and E2E validation. Extending that workflow with a confirmed continuous delivery step, such as deployment or release publication, is a possible next improvement.

## License

See [LICENSE](LICENSE) for the project's terms of use.
