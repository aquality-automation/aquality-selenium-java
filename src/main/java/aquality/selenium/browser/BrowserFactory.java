package aquality.selenium.browser;

import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.core.logging.Logger;

abstract class BrowserFactory implements IBrowserFactory {

    IllegalArgumentException getLoggedWrongBrowserNameException() {
        String message = getLocManager().getValue("loc.browser.name.wrong");
        IllegalArgumentException exception = new IllegalArgumentException(message);
        Logger.getInstance().fatal(message, exception);
        return exception;
    }

    void logBrowserIsReady(BrowserName browserName) {
        Logger.getInstance().info(getLocManager().getValue("loc.browser.ready"), browserName.toString());
    }

    private LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }
}
