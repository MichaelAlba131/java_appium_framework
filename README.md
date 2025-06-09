<!DOCTYPE html>
<html lang="pt-BR">
<body>

<h1>📱 Projeto de Testes Automatizados Mobile com Appium + Java</h1>

<p>Este projeto realiza testes automatizados em um aplicativo Android utilizando <strong>Appium</strong>, com suporte a <strong>Java</strong>, <strong>Cucumber (BDD)</strong> e execução via <strong>JUnit 5</strong>.</p>

<hr>

<h2 class="emoji">🚀 Visão Geral</h2>
<ul>
  <li>📲 Automatização de testes mobile</li>
  <li>☕️ Java + Appium + Cucumber + JUnit 5</li>
  <li>🤖 Execução em emulador Android (AVD)</li>
  <li>📊 Geração de relatórios HTML e JSON</li>
  <li>🧪 Testes escritos em Gherkin (BDD)</li>
</ul>

<hr>

<h2 class="emoji">📂 Estrutura do Projeto</h2>
<pre>
.
├── src/
│   ├── main/java/
│   │   ├── pages/
│   │   ├── interactions/
│   │   └── support/
│   └── test/java/
│       ├── runner/
│       └── steps/
│   └── test/resources/
│       └── feature/
├── pom.xml
└── target/
    ├── cucumber/html/
    ├── cucumber/report.json
    └── surefire-reports/
</pre>

<hr>

<h2 class="emoji">🧪 Como Executar os Testes</h2>
<ol>
  <li>✅ Certifique-se que o ambiente possui:
    <ul>
      <li>Java 17+</li>
      <li>Node.js e Appium instalados</li>
      <li>ANDROID_HOME configurado</li>
      <li>Emulador Android configurado</li>
    </ul>
  </li>
  <li>🧬 Execute os testes com Maven:</li>
</ol>

```bash
  mvn clean test
```

<p><strong>Relatórios gerados:</strong></p>
<ul>
  <li>📄 <code>target/cucumber/html/index.html</code>: relatório HTML detalhado</li>
  <li>📄 <code>target/cucumber/report.json</code>: relatório em JSON</li>
  <li>📄 <code>target/surefire-reports/</code>: resultados JUnit</li>
</ul>

<hr>

<h2 class="emoji">🧰 Tecnologias e Ferramentas</h2>

<table>
  <tr><th>Tecnologia</th><th>Função</th></tr>
  <tr><td>Java 17+</td><td>Linguagem principal</td></tr>
  <tr><td>Appium</td><td>Automação mobile</td></tr>
  <tr><td>Cucumber</td><td>BDD com Gherkin</td></tr>
  <tr><td>JUnit 5</td><td>Executor dos testes</td></tr>
  <tr><td>Selenium</td><td>Interação com elementos</td></tr>
  <tr><td>Maven</td><td>Build e execução</td></tr>
</table>

<hr>

<h2 class="emoji">🧠 Organização das Camadas</h2>
<ul>
  <li><strong>support</strong>: Inicialização do Appium, driver e emulador</li>
  <li><strong>pages</strong>: Elementos do app (Page Object)</li>
  <li><strong>interactions</strong>: Lógica das interações</li>
  <li><strong>steps</strong>: Implementação dos passos do Cucumber</li>
  <li><strong>hooks</strong>: Setup e teardown global</li>
  <li><strong>runner</strong>: Executor JUnit + Cucumber</li>
</ul>

<hr>

<h2 class="emoji">✅ Exemplo de Cenário</h2>
<pre>
Feature: Login do aplicativo

  Scenario: Login com credenciais válidas
    Given permitir acessos ao app
    And clicar em login
    When eu insiro o usuario "usuario_teste" e a senha "senha_teste"
</pre>

<hr>

<h2 class="emoji">📈 Resultado Visual</h2>
<p>Após a execução, acesse:</p>
<ul>
  <li>📂 <code>target/cucumber/html/index.html</code></li>
</ul>
<p>Abrir no navegador para visualizar o relatório completo dos testes.</p>
  


  <p>Os relatórios estarão disponíveis na pasta <code>target/cucumber</code>.</p>
</section>

<section>
  <h2>🔧 Configurações Importantes</h2>
  <ul>
    <li><code>DeviceManager</code>: cria e controla o emulador Android.</li>
    <li><code>AppiumServerManager</code>: inicia e para o servidor Appium automaticamente.</li>
    <li><code>DriverFactory</code>: inicializa o driver do Appium para os testes.</li>
  </ul>
</section>

<section>
  <h2>📚 Melhores Práticas e Dicas</h2>
  <ul>
    <li>Use esperas explícitas para garantir estabilidade dos testes (ex.: <code>WebDriverWait</code>).</li>
    <li>Centralize seletores na classe Page Objects para facilitar manutenção.</li>
    <li>Separe responsabilidades: interações na camada <code>interactions</code>, passos no <code>steps</code>.</li>
    <li>Automatize o ciclo completo: start emulador → start Appium → rodar testes → stop Appium → stop emulador.</li>
  </ul>
</section>


<h2>🪪 Licença</h2>
<p>
Distribuído sob a licença MIT. Veja o arquivo <code>LICENSE</code> para mais informações.
</p>

</body>
</html>
