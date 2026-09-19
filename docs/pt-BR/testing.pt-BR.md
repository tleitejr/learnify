![Learnify](../img/banner.png "Learnify")

<details> 
	<summary><strong>Idioma - Português (Brasil)</strong></summary>
	<br>
	<ul>
	    <li><a href="../testing.md">English</a></li>
        <li><a href="testing.pt-BR.md">Português (Brasil)</a></li>
	</ul>
</details>
<br>

# Testes

### Testes do frontend

Testes unitários e de componentes do frontend usam Vitest, Testing Library, `@testing-library/user-event`, jsdom e handlers MSW para simulação da API. O Playwright executa cenários ponta a ponta no Chromium. A configuração do Playwright inicia um servidor Vite em `http://127.0.0.1:4173`.

Em `learnify-web/`:

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

O Playwright pode exigir a instalação de seu navegador em uma nova máquina, de acordo com a documentação do Playwright e o ambiente local.

### Testes do backend

Testes unitários do backend são executados no JUnit 5 e usam H2 onde um banco de dados em memória é apropriado. Testes de integração usam Testcontainers com PostgreSQL e, portanto, exigem um ambiente Docker funcional.

Em `learnify-api/`:

```bash
./gradlew test
./gradlew integrationTest
./gradlew check
./gradlew jacocoTestCoverageVerification
```

`check` inclui a tarefa `integrationTest`. A tarefa Gradle `test` produz dados de execução do JaCoCo, e as tarefas de relatório geram saída HTML de cobertura em `learnify-api/build/.coverage`. A verificação de cobertura atualmente exige 90% de cobertura de linha, instrução, método e classe, e 75% de cobertura de branch após as exclusões configuradas.

### Ferramentas necessárias

- Node.js e npm para testes do frontend.
- Java 21 e o Gradle Wrapper para testes do backend.
- Docker para testes de integração baseados em Testcontainers.
- Navegador Chromium do Playwright para testes E2E.

### GitHub Actions

O repositório inclui um fluxo de trabalho de integração contínua em `.github/workflows/ci.yml`. Ele é executado em pushes e pull requests direcionados à `main`, detecta se arquivos da API ou da web foram alterados e pode executar as verificações correspondentes. O fluxo de trabalho executa testes unitários e de integração do backend com verificação de cobertura, linting do frontend, verificação de tipos e build, testes unitários do frontend com cobertura e testes E2E com Playwright. Ele também faz upload de relatórios de testes e cobertura como artefatos do fluxo de trabalho.

No momento, o repositório não mostra uma etapa confirmada de entrega contínua, como implantação ou publicação de release.
