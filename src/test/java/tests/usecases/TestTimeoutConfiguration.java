package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

public class TestTimeoutConfiguration {
    private static final String TIMEOUT_KEY = "timeouts.timeoutCondition";


    @AfterMethod
    private void after(){
        System.clearProperty(TIMEOUT_KEY);
        Configuration.injectDependencies(null);
    }

    @Test
    public void testNumberFormatExceptionShouldBeThrownIfTimeoutIsNotANumber() {

        Assert.assertThrows(Exception.class, () -> {
            System.setProperty(TIMEOUT_KEY, "abcdef");
            Configuration.injectDependencies(null);
            BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
                });

        try {
            BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        } catch (Exception e){
            Assert.assertSame(e.getCause().getClass(), NumberFormatException.class);
            Assert.assertTrue(e.getMessage().contains("NumberFormatException"));
        }
    }
}
