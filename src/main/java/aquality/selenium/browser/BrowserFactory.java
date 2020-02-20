package aquality.selenium.browser;

import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;

public abstract class BrowserFactory implements IBrowserFactory {

    IllegalArgumentException getLoggedWrongBrowserNameException() {
        String message = AqualityServices.get(ILocalizationManager.class).getLocalizedMessage("loc.browser.name.wrong");
        IllegalArgumentException exception = new IllegalArgumentException(message);
        Logger.getInstance().fatal(message, exception);
        return exception;
    }

    void logBrowserIsReady(BrowserName browserName) {
        AqualityServices.getLocalizedLogger().info("loc.browser.ready", browserName.toString());
    }
}
