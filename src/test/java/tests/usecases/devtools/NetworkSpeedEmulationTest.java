package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.NetworkHandling;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.devtools.v143.network.model.ConnectionType;
import org.openqa.selenium.devtools.v143.network.model.NetworkConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NetworkSpeedEmulationTest extends BaseTest {

    private static final int DOWNLOAD_THROUGHPUT = 53;
    private static final int DOWNLOAD_THROUGHPUT_BIG = 530000;
    private static final int UPLOAD_THROUGHPUT = 27;
    private static final int UPLOAD_THROUGHPUT_BIG = 270000;
    private static final int LATENCY = 27;

    private final WelcomeForm welcomeForm = new WelcomeForm();

    private NetworkHandling network() {
        return getBrowser().devTools().network();
    }

    private List<NetworkConditions> getNetworkConditions(int downloadThroughput, int uploadThroughput, ConnectionType connectionType) {
        return Collections.singletonList(new NetworkConditions(welcomeForm.getUrl(), LATENCY, downloadThroughput, uploadThroughput,
                Optional.of(connectionType), Optional.empty(), Optional.empty(), Optional.empty()));
    }
    private List<NetworkConditions> getNetworkConditions(int downloadThroughput, int uploadThroughput) {
        return Collections.singletonList(new NetworkConditions(welcomeForm.getUrl(), LATENCY, downloadThroughput, uploadThroughput,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Test
    public void networkSpeedEmulationTest() {
        getBrowser().setPageLoadTimeout(AqualityServices.getConfiguration().getTimeoutConfiguration().getScript());
        getBrowser().goTo(welcomeForm.getUrl());
        Assert.assertTrue(welcomeForm.state().waitForDisplayed(), "Form must be opened");
        boolean isOffline = true;
        network().emulateConditionsByRule(isOffline, getNetworkConditions(-1, -1));
        network().overrideState(isOffline, LATENCY, -1, -1);
        getBrowser().refresh();
        getBrowser().waitForPageToLoad();
        Assert.assertFalse(welcomeForm.state().isDisplayed(), "Form must not be opened when offline");
        isOffline = false;
        network().emulateConditionsByRule(isOffline, getNetworkConditions(DOWNLOAD_THROUGHPUT, UPLOAD_THROUGHPUT, ConnectionType.BLUETOOTH));
        network().overrideState(isOffline, LATENCY, DOWNLOAD_THROUGHPUT, UPLOAD_THROUGHPUT, ConnectionType.BLUETOOTH);
        Assert.assertThrows(TimeoutException.class, () -> getBrowser().refresh());
        network().emulateConditionsByRule(isOffline, getNetworkConditions(DOWNLOAD_THROUGHPUT_BIG, UPLOAD_THROUGHPUT_BIG, ConnectionType.CELLULAR4G));
        getBrowser().refresh();
        Assert.assertTrue(welcomeForm.state().waitForDisplayed(), "Form must be opened when online with good speed");
    }
}
