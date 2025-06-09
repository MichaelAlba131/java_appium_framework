package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utils {

    public static void waitForVisibilityWithRetries(WebDriver driver, WebElement element, int maxAttempts, int waitSeconds) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
                wait.until(ExpectedConditions.visibilityOf(element));
                // Se chegou aqui, elemento está visível e não stale
                return;
            } catch (StaleElementReferenceException staleEx) {
                attempts++;
                System.out.println("Tentativa " + attempts + " falhou por StaleElementReferenceException. Tentando novamente...");
            } catch (TimeoutException timeoutEx) {
                attempts++;
                System.out.println("Tentativa " + attempts + " falhou por TimeoutException. Tentando novamente...");
            } catch (Exception ex) {
                attempts++;
                System.out.println("Tentativa " + attempts + " falhou por exceção inesperada: " + ex.getMessage());
            }

            if (attempts == maxAttempts) {
                throw new TimeoutException("Elemento não ficou visível após " + maxAttempts + " tentativas.");
            }

            try {
                Thread.sleep(500); // espera meio segundo antes da próxima tentativa
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}