![Learnify](img/banner.png "Learnify")

<details>
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="testing.md">English</a></li>
        <li><a href="pt-BR/testing.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Testing

### Frontend tests

Frontend unit and component tests use Vitest, Testing Library, `@testing-library/user-event`, jsdom, and MSW handlers for API mocking. Playwright runs end-to-end scenarios in Chromium. The Playwright configuration starts a Vite server on `http://127.0.0.1:4173`.

From `learnify-web/`:

```bash
npm ci
npm run lint
npm run test
npm run test:coverage
npm run test:watch
npm run test:ui
npm run test:e2e
npm run test:e2e:ui
npm run test:e2e:headed
npm run test:e2e:debug
```

Playwright may require its browser installation on a new machine according to the Playwright documentation and local environment.

### Backend tests

Backend unit tests run on JUnit 5 and use H2 where an in-memory database is appropriate. Integration tests use Testcontainers with PostgreSQL and therefore require a working Docker environment.

From `learnify-api/`:

```bash
./gradlew test
./gradlew integrationTest
./gradlew check
./gradlew jacocoTestCoverageVerification
```

`check` includes the `integrationTest` task. The Gradle `test` task produces JaCoCo execution data and the report tasks generate HTML coverage output under `learnify-api/build/.coverage`. Coverage verification currently requires 90% line, instruction, method, and class coverage, and 75% branch coverage after the configured exclusions.

### Required tooling

- Node.js and npm for frontend tests.
- Java 21 and the Gradle Wrapper for backend tests.
- Docker for Testcontainers-based integration tests.
- Playwright's Chromium browser for E2E tests.

### GitHub Actions

The repository includes a continuous integration workflow at `.github/workflows/ci.yml`. It runs on pushes and pull requests targeting `main`, detects whether API or web files changed, and can execute the corresponding checks. The workflow runs backend unit and integration tests with coverage verification, frontend linting, type-checking and build, frontend unit tests with coverage, and Playwright E2E tests. It also uploads test and coverage reports as workflow artifacts.

The repository does not currently show a confirmed continuous delivery step such as deployment or release publication.
