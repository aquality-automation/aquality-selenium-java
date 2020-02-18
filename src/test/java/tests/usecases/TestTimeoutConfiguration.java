package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.configuration.*;
import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTimeoutConfiguration {

    @Test
    public void testNumberFormatExceptionShouldBeThrownIfTimeoutIsNotANumber() {
        Assert.assertThrows(NumberFormatException.class, () ->
                AqualityServices.setBrowser(new Browser(null, configuration)));
    }

    IConfiguration configuration = new IConfiguration() {
        @Override
        public IBrowserProfile getBrowserProfile() {
            return Configuration.getInstance().getBrowserProfile();
        }

        @Override
        public ITimeoutConfiguration getTimeoutConfiguration() {
            return new TimeoutConfiguration(new JsonSettingsFile("settings.incorrect.json"));
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
