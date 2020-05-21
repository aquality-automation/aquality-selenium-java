package aquality.selenium.browser;

import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.IActionRetrier;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;

interface BrowserFactory extends IBrowserFactory {

    default IllegalArgumentException getLoggedWrongBrowserNameException() {
        String message = AqualityServices.get(ILocalizationManager.class).getLocalizedMessage("loc.browser.name.wrong");
        IllegalArgumentException exception = new IllegalArgumentException(message);
        Logger.getInstance().fatal(message, exception);
        return exception;
    }

    default void logBrowserIsReady(BrowserName browserName) {
        AqualityServices.getLocalizedLogger().info("loc.browser.ready", browserName.toString());
    }

    default <T extends RemoteWebDriver> T getDriver(Class<T> driverClass, Capabilities capabilities) {
        return getDriver(driverClass, null, capabilities);
    }

    default <T extends RemoteWebDriver> T getDriver(Class<T> driverClass, CommandExecutor commandExecutor, Capabilities capabilities) {
        return AqualityServices.get(IActionRetrier.class).doWithRetry(() -> {
            try {
                if (commandExecutor != null) {
                    return driverClass.getDeclaredConstructor(CommandExecutor.class, Capabilities.class).newInstance(commandExecutor, capabilities);
                }

                return driverClass.getDeclaredConstructor(Capabilities.class).newInstance(capabilities);
            } catch (ReflectiveOperationException e) {
                throw new UnsupportedOperationException(String.format("Cannot instantiate driver with type '%1$s'", driverClass), e);
            }
        }, Collections.emptyList());
    }
}
