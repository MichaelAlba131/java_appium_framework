@login @loginFeature @All
Feature: Login no aplicativo

  @loginSucesso
  Scenario: Login com sucesso
    Given permitir acessos ao app
    And clicar em login
    When eu insiro o usuario "admin" e a senha "admin123"