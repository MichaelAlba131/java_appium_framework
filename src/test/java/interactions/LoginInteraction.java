package interactions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import support.DriverFactory;
import support.Utils;

import java.time.Duration;

public class LoginInteraction extends pages.LoginPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public LoginInteraction() {
        this.driver = DriverFactory.getDriver();
        // Wait up to 10 seconds for elements to be visible
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clicarPermitirAcesso() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(allow));
        allow.click();
    }

    public void clicaremLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logIn));
        logIn.click();
    }

    public void fazerLogin(String user, String password) throws InterruptedException {
        Thread.sleep(1000);
        Utils.waitForVisibilityWithRetries(driver, usuario, 15, 10);
        usuario.clear();
        usuario.sendKeys(user);

        senha.clear();
        senha.sendKeys(password);

        entrar.click();
    }
}