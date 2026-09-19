![Learnify](img/banner.png "Learnify")

<details> 
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="environment-variables.md">English</a></li>
        <li><a href="pt-BR/environment-variables.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Environment Variables

The Compose file loads local environment files from `learnify-db/.env/`:

- `.env.db` for PostgreSQL.
- `.env.api` for the Spring Boot API.
- `.env.web` for the frontend container.

The repository includes safe templates named `.env.*.example`. After cloning, run `./scripts/setup-local.sh` from the repository root. The script creates the local files, generates a JWT secret, and leaves existing local files unchanged.

### Variables

| Variable                     | Used by | Purpose                                                                         | Example                                           |
| ---------------------------- | ------- | ------------------------------------------------------------------------------- | ------------------------------------------------- |
| `POSTGRES_DB`                | `db`    | PostgreSQL database name                                                        | `learnify_local`                                  |
| `POSTGRES_USER`              | `db`    | PostgreSQL user                                                                 | `learnify_user`                                   |
| `POSTGRES_PASSWORD`          | `db`    | PostgreSQL password                                                             | `replace-with-a-local-password`                   |
| `SPRING_PROFILES_ACTIVE`     | `api`   | Active Spring profile                                                           | `dev`                                             |
| `SPRING_DATASOURCE_URL`      | `api`   | JDBC URL for the database                                                       | `jdbc:postgresql://localhost:5432/learnify_local` |
| `SPRING_DATASOURCE_USERNAME` | `api`   | Database username used by the API                                               | `learnify_user`                                   |
| `SPRING_DATASOURCE_PASSWORD` | `api`   | Database password used by the API                                               | `replace-with-a-local-password`                   |
| `JWT_SECRET`                 | `api`   | Secret used to sign JWTs                                                        | `use-a-long-random-local-secret`                  |
| `DATA_LOCATIONS`             | `api`   | Location of SQL seed data                                                       | `file:/data/data.sql`                             |
| `CHOKIDAR_USEPOLLING`        | `web`   | Enables file polling for frontend file watching in the container                | `true`                                            |
| `VITE_API_URL`               | `web`   | Optional API base URL; defaults to `http://localhost:8080` in the frontend code | `http://localhost:8080`                           |

The Compose API connects to the database using the service hostname `db`; a local API process normally uses `localhost` instead. `DATA_LOCATIONS` must point to a location readable by the API process.

Never publish real passwords, JWT secrets, access tokens, API keys, or other credentials. The example password is only for the local academic stack and must not be reused in a public deployment. Keep generated local files outside version control.
