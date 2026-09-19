![Learnify](../img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="../setup.md">English</a></li>
        <li><a href="setup.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Configuração

### Pré-requisitos

Para a stack completa:

- Docker com Docker Compose V2 (`docker compose`).

Para executar módulos fora de contêineres:

- Java 21 para a API.
- Node.js. A imagem do frontend usa Node.js 24 Alpine.
- Uma instância PostgreSQL acessível pela API.

### Docker Compose

Execute estes comandos a partir da raiz do repositório:

```bash
./scripts/setup-local.sh
docker compose up --build
```

Para executar em segundo plano:

```bash
docker compose up --build -d
```

Acompanhe os logs dos serviços:

```bash
docker compose logs -f
```

Pare os serviços:

```bash
docker compose down
```

Pare os serviços e remova volumes, incluindo dados persistidos do banco de dados:

```bash
docker compose down -v
```

Os endpoints locais são:

| Serviço    | URL                     |
| ---------- | ----------------------- |
| Web        | `http://localhost:5173` |
| API        | `http://localhost:8080` |
| PostgreSQL | `localhost:5432`        |

O Compose lê os arquivos de ambiente dos serviços em `learnify-db/.env/`. O script de configuração os cria a partir dos exemplos versionados. Eles contêm valores apenas locais e são ignorados pelo Git.

### Executando o frontend localmente

Instale as dependências e inicie o Vite:

```bash
cd learnify-web
npm ci
npm run dev
```

O Vite escuta na porta `5173`. Defina `VITE_API_URL` antes de iniciá-lo quando a API não estiver disponível em `http://localhost:8080`.

### Executando o backend localmente

Antes de iniciar a API, configure `SPRING_PROFILES_ACTIVE`, `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET` e `DATA_LOCATIONS` para uma instância PostgreSQL acessível. Em seguida, execute:

```bash
cd learnify-api
./gradlew bootRun
```

A API escuta na porta `8080` por padrão. A configuração do Docker Compose fornece o ambiente da API a partir de `learnify-db/.env/.env.api` e monta o diretório de dados SQL em `/data`.
