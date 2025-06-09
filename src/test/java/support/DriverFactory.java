package support;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static AppiumDriver driver;

    public static AppiumDriver getDriver() {
        return driver;
    }

    public static void iniciarApp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", "apk/Instagram.apk");

        System.out.println("ANDROID_HOME: " + System.getenv("ANDROID_HOME"));
        System.out.println("ANDROID_SDK_ROOT: " + System.getenv("ANDROID_SDK_ROOT"));

        driver = new AndroidDriver(new URL("http://localhost:4723/"), caps);
    }

    public static void fecharApp() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}