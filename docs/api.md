![Learnify](img/banner.png "Learnify")

<details> 
	<summary><strong>Language - English</strong></summary>
	<br>
	<ul>
	    <li><a href="api.md">English</a></li>
        <li><a href="pt-BR/api.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>

# API

### Base paths and documentation

The API uses the `/api/v1` prefix. The OpenAPI specification is maintained at [`learnify-api/src/main/resources/static/openapi.yml`](../learnify-api/src/main/resources/static/openapi.yml).

When the API is running locally:

- Interactive API documentation: `http://localhost:8080/v1/docs`
- Health check: `http://localhost:8080/actuator/health`

The Actuator configuration exposes the health endpoint.

### Main resources

The API provides resources for:

- Authentication and account management.
- Disciplines and their contents.
- Quizzes and quiz answers.
- Authenticated user profile.
- Dashboard data, progress, statistics and achievements.
- User ranking.

The exact paths, request bodies, responses and security declarations are defined in the OpenAPI specification.

### Authentication

The web client authenticates through the API and stores the resulting JWT in the browser. Requests to protected resources send it through the `Authorization` header. The backend uses Spring Security and JJWT to protect and validate authenticated requests. Do not place real tokens or signing secrets in source control or documentation.
