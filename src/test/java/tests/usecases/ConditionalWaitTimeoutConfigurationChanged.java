package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

public class ConditionalWaitTimeoutConfigurationChanged {
    private static final String TIMEOUT_KEY = "timeouts.timeoutCondition";
    private static final String FALSE_URL_PART = "invalid";

    @BeforeTest
    private void before(){
        System.setProperty(TIMEOUT_KEY, "abcdef");
    }

    @AfterTest
    private void after(){
        System.clearProperty(TIMEOUT_KEY);
    }

    @Test
    public void testSmartWaitForShouldThrowInvalidConfigurationException() {

        Assert.assertThrows(NumberFormatException.class, () ->
        {
            BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
            Assert.assertNotNull(ConditionalWait.waitFor(ExpectedConditions::urlContains, FALSE_URL_PART));
        });
    }

    @Test
    public void testSmartWaitForTrueShouldThrowInvalidConfigurationException() {

        Assert.assertThrows(NumberFormatException.class, () ->
        {
            BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
            Assert.assertTrue(ConditionalWait.waitForTrue(ExpectedConditions.urlContains(FALSE_URL_PART)));
        });
    }
}
