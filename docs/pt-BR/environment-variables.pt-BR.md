![Learnify](../img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="../environment-variables.md">English</a></li>
        <li><a href="environment-variables.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Variáveis de Ambiente

O arquivo Compose carrega arquivos de ambiente locais de `learnify-db/.env/`:

- `.env.db` para PostgreSQL.
- `.env.api` para a API Spring Boot.
- `.env.web` para o contêiner do frontend.

O repositório inclui modelos seguros chamados `.env.*.example`. Após clonar, execute `./scripts/setup-local.sh` a partir da raiz do repositório. O script cria os arquivos locais, gera um segredo JWT e deixa arquivos locais existentes inalterados.

### Variáveis

| Variável                     | Usado por | Finalidade                                                                         | Exemplo                                           |
| ---------------------------- | --------- | ---------------------------------------------------------------------------------- | ------------------------------------------------- |
| `POSTGRES_DB`                | `db`      | Nome do banco de dados PostgreSQL                                                  | `learnify_local`                                  |
| `POSTGRES_USER`              | `db`      | Usuário do PostgreSQL                                                              | `learnify_user`                                   |
| `POSTGRES_PASSWORD`          | `db`      | Senha do PostgreSQL                                                                | `replace-with-a-local-password`                   |
| `SPRING_PROFILES_ACTIVE`     | `api`     | Perfil Spring ativo                                                                | `dev`                                             |
| `SPRING_DATASOURCE_URL`      | `api`     | URL JDBC para o banco de dados                                                     | `jdbc:postgresql://localhost:5432/learnify_local` |
| `SPRING_DATASOURCE_USERNAME` | `api`     | Nome de usuário do banco de dados usado pela API                                   | `learnify_user`                                   |
| `SPRING_DATASOURCE_PASSWORD` | `api`     | Senha do banco de dados usada pela API                                             | `replace-with-a-local-password`                   |
| `JWT_SECRET`                 | `api`     | Segredo usado para assinar JWTs                                                    | `use-a-long-random-local-secret`                  |
| `DATA_LOCATIONS`             | `api`     | Local dos dados de seed SQL                                                        | `file:/data/data.sql`                             |
| `CHOKIDAR_USEPOLLING`        | `web`     | Habilita polling de arquivos para observação de arquivos do frontend no contêiner  | `true`                                            |
| `VITE_API_URL`               | `web`     | URL base opcional da API; o padrão no código do frontend é `http://localhost:8080` | `http://localhost:8080`                           |

A API do Compose se conecta ao banco de dados usando o hostname de serviço `db`; um processo local da API normalmente usa `localhost` em vez disso. `DATA_LOCATIONS` deve apontar para um local legível pelo processo da API.

Nunca publique senhas reais, segredos JWT, tokens de acesso, chaves de API ou outras credenciais. A senha de exemplo é apenas para a stack acadêmica local e não deve ser reutilizada em uma implantação pública. Mantenha arquivos locais gerados fora do controle de versão.
