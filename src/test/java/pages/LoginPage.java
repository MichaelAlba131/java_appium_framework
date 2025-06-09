package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import support.DriverFactory;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@text,'Allow')]")
    public WebElement allow;

    @FindBy(how = How.XPATH, using = "//android.view.ViewGroup[@clickable='true' and @enabled='true' and @focusable='true' and @bounds='[32,1211][1049,1326]']")
    public WebElement logIn;

    @FindBy(how = How.XPATH, using = "//android.widget.MultiAutoCompleteTextView[@index='3']")
    public WebElement usuario;

    @FindBy(how = How.XPATH, using = "//android.widget.FrameLayout[@resource-id='com.instagram.lite:id/main_layout']/android.widget.FrameLayout/android.view.ViewGroup[3]/android.widget.MultiAutoCompleteTextView[2]")
    public WebElement senha;

    @FindBy(how = How.XPATH, using = "(//android.view.ViewGroup)[8]")
    public WebElement entrar;

    @FindBy(how = How.ID, using = "home")
    public WebElement home;

}