package steps;

import interactions.LoginInteraction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class LoginSteps {
    private LoginInteraction login = new LoginInteraction();

    @Given("permitir acessos ao app")
    public void o_app_esta_aberto() {
        login.clicarPermitirAcesso();
    }

    @And("clicar em login")
    public void clicarEmLogin() {
        login.clicaremLogin();
    }

    @When("eu insiro o usuario {string} e a senha {string}")
    public void eu_insiro_o_usuario_e_a_senha(String user, String pass) throws InterruptedException {
        login.fazerLogin(user, pass);
    }
}