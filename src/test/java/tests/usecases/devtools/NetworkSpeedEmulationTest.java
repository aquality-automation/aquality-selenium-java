package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

public class NetworkSpeedEmulationTest extends BaseTest {

    private static final int DOWNLOAD_THROUGHPUT = 53;
    private static final int DOWNLOAD_THROUGHPUT_BIG = 530000;
    private static final int UPLOAD_THROUGHPUT = 27;
    private static final int UPLOAD_THROUGHPUT_BIG = 270000;
    private static final int LATENCY = 27;

    @Test
    public void networkSpeedEmulationTest() {
        getBrowser().setPageLoadTimeout(AqualityServices.getConfiguration().getTimeoutConfiguration().getScript());
        WelcomeForm welcomeForm = new WelcomeForm();
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertTrue(welcomeForm.state().waitForDisplayed(), "Form must be opened");
        boolean isOffline = true;
        getBrowser().devTools().network().emulateConditions(isOffline, LATENCY, -1, -1);
        getBrowser().refresh();
        getBrowser().waitForPageToLoad();
        Assert.assertFalse(welcomeForm.state().isDisplayed(), "Form must not be opened when offline");
        isOffline = false;
        getBrowser().devTools().network().emulateConditions(isOffline, LATENCY, DOWNLOAD_THROUGHPUT, UPLOAD_THROUGHPUT, ConnectionType.BLUETOOTH.toString());
        Assert.assertThrows(TimeoutException.class, () -> getBrowser().refresh());
        getBrowser().devTools().network().emulateConditions(isOffline, LATENCY, DOWNLOAD_THROUGHPUT_BIG, UPLOAD_THROUGHPUT_BIG, ConnectionType.CELLULAR4G.toString());
        getBrowser().refresh();
        Assert.assertTrue(welcomeForm.state().waitForDisplayed(), "Form must be opened when online with good speed");
    }
}
