![Learnify](../img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="../api.md">English</a></li>
        <li><a href="api.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>

# API

### Caminhos base e documentação

A API usa o prefixo `/api/v1`. A especificação OpenAPI é mantida em [`learnify-api/src/main/resources/static/openapi.yml`](../learnify-api/src/main/resources/static/openapi.yml).

Quando a API está em execução localmente:

- Documentação interativa da API: `http://localhost:8080/v1/docs`
- Verificação de integridade: `http://localhost:8080/actuator/health`

A configuração do Actuator expõe o endpoint de health.

### Principais recursos

A API fornece recursos para:

- Autenticação e gerenciamento de conta.
- Disciplinas e seus conteúdos.
- Quizzes e respostas de quiz.
- Perfil do usuário autenticado.
- Dados de dashboard, progresso, estatísticas e conquistas.
- Ranking de usuários.

Os caminhos exatos, corpos de requisição, respostas e declarações de segurança são definidos na especificação OpenAPI.

### Autenticação

O cliente web autentica-se por meio da API e armazena o JWT resultante no navegador. Requisições para recursos protegidos o enviam por meio do cabeçalho `Authorization`. O backend usa Spring Security e JJWT para proteger e validar requisições autenticadas. Não coloque tokens reais ou segredos de assinatura no controle de código-fonte ou na documentação.
