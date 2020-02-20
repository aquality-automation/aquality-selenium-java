package aquality.selenium.browser;

import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;

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
}
