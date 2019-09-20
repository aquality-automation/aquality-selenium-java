package tests.usecases;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.*;
import aquality.selenium.utils.JsonFile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTimeoutConfiguration {

    @Test
    public void testNumberFormatExceptionShouldBeThrownIfTimeoutIsNotANumber() {
        Assert.assertThrows(NumberFormatException.class, () ->
        {
            BrowserManager.setBrowser(new Browser(null, configuration));
        });
    }

    IConfiguration configuration = new IConfiguration() {
        @Override
        public IBrowserProfile getBrowserProfile() {
            return Configuration.getInstance().getBrowserProfile();
        }

        @Override
        public ITimeoutConfiguration getTimeoutConfiguration() {
            return new TimeoutConfiguration(new JsonFile("settings.incorrect.json"));
        }

        @Override
        public IRetryConfiguration getRetryConfiguration() {
            return Configuration.getInstance().getRetryConfiguration();
        }

        @Override
        public ILoggerConfiguration getLoggerConfiguration() {
            return Configuration.getInstance().getLoggerConfiguration();
        }
    };
}
