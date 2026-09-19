![Learnify](img/banner.png "Learnify")

<details> 
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="setup.md">English</a></li>
        <li><a href="pt-BR/setup.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Setup

### Prerequisites

For the complete stack:

- Docker with Docker Compose V2 (`docker compose`).

For running modules outside containers:

- Java 21 for the API.
- Node.js. The frontend image uses Node.js 24 Alpine.
- A PostgreSQL instance reachable by the API.

### Docker Compose

Run these commands from the repository root:

```bash
./scripts/setup-local.sh
docker compose up --build
```

To run in the background:

```bash
docker compose up --build -d
```

Follow service logs:

```bash
docker compose logs -f
```

Stop the services:

```bash
docker compose down
```

Stop the services and remove volumes, including persisted database data:

```bash
docker compose down -v
```

The local endpoints are:

| Service    | URL                     |
| ---------- | ----------------------- |
| Web        | `http://localhost:5173` |
| API        | `http://localhost:8080` |
| PostgreSQL | `localhost:5432`        |

Compose reads service environment files from `learnify-db/.env/`. The setup script creates them from the tracked examples. They contain local-only values and are ignored by Git.

### Running the frontend locally

Install dependencies and start Vite:

```bash
cd learnify-web
npm ci
npm run dev
```

Vite listens on port `5173`. Set `VITE_API_URL` before starting it when the API is not available at `http://localhost:8080`.

### Running the backend locally

Before starting the API, configure `SPRING_PROFILES_ACTIVE`, `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, and `DATA_LOCATIONS` for a reachable PostgreSQL instance. Then run:

```bash
cd learnify-api
./gradlew bootRun
```

The API listens on port `8080` by default. The Docker Compose setup supplies the API environment from `learnify-db/.env/.env.api` and mounts the SQL data directory at `/data`.
