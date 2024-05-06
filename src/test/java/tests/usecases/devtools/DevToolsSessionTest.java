package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.DevToolsHandling;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

public class DevToolsSessionTest extends BaseTest {
    private static DevToolsHandling devTools() {
        return AqualityServices.getBrowser().devTools();
    }

    @Test
    public void getAndCloseDevToolsSessionTest() {
        String windowHandle = getBrowser().tabs().getCurrentHandle();
        Assert.assertFalse(devTools().hasActiveDevToolsSession(), "No DevTools session should be running initially");
        Assert.assertNotNull(devTools().getDevToolsSession(windowHandle), "Should be possible to get DevTools session");
        Assert.assertTrue(devTools().hasActiveDevToolsSession(), "DevTools session should be indicated as active after getting");
        devTools().closeDevToolsSession();
        Assert.assertFalse(devTools().hasActiveDevToolsSession(), "DevTools session should be indicated as not active after close");
        Assert.assertNotNull(devTools().getDevToolsSession(), "Should be possible to get a new DevTools session after close");
        Assert.assertTrue(devTools().hasActiveDevToolsSession(), "DevTools session should be indicated as active after getting for a second time");
    }
}
