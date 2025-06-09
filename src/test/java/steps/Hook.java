package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import support.AppiumServerManager;
import support.DeviceManager;
import support.DriverFactory;

import java.io.IOException;

public class Hook {

    @BeforeAll
    public static void globalSetUp() throws IOException, InterruptedException {
        //DeviceManager.startEmulator();
        AppiumServerManager.startAppiumServer();
    }

    @AfterAll
    public static void globalTearDown() throws IOException, InterruptedException {
        AppiumServerManager.stopAppiumServer();
        //DeviceManager.stopEmulator();
    }

    @Before
    public void setUp() throws IOException {
        DriverFactory.iniciarApp();
    }

    @After
    public void tearDown() {
        DriverFactory.fecharApp();
    }
}