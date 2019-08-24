package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

public class TestTimeoutConfiguration {
    private static final String TIMEOUT_KEY = "timeouts.timeoutCondition";

    @BeforeMethod
    private void before(){
        System.setProperty(TIMEOUT_KEY, "abcdef");
    }

    @AfterMethod
    private void after(){
        System.clearProperty(TIMEOUT_KEY);
    }

    @Test
    public void testNumberFormatExceptionShouldBeThrownIfTimeoutIsNotANumber() {

        Assert.assertThrows(NumberFormatException.class, () ->
        {
            BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        });
    }
}
