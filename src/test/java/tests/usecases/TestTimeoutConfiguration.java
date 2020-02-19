package tests.usecases;

import aquality.selenium.configuration.TimeoutConfiguration;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTimeoutConfiguration {

    @Test
    public void testNumberFormatExceptionShouldBeThrownIfTimeoutIsNotANumber() {
        Assert.assertThrows(NumberFormatException.class, () ->
                new TimeoutConfiguration(new JsonSettingsFile("settings.incorrect.json")));
    }
}
