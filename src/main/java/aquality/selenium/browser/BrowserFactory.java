package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IActionRetrier;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.Arrays;

public abstract class BrowserFactory implements IBrowserFactory {

    private final IActionRetrier actionRetrier;
    private final IBrowserProfile browserProfile;
    private final ILocalizedLogger localizedLogger;

    protected BrowserFactory(IActionRetrier actionRetrier, IBrowserProfile browserProfile, ILocalizedLogger localizedLogger) {

        this.actionRetrier = actionRetrier;
        this.browserProfile = browserProfile;
        this.localizedLogger = localizedLogger;
    }

    protected abstract RemoteWebDriver getDriver();

    @Override
    public Browser getBrowser() {
        RemoteWebDriver driver = actionRetrier.doWithRetry(
                this::getDriver,
                Arrays.asList(SessionNotCreatedException.class, UnreachableBrowserException.class, WebDriverException.class));
        Browser browser = new Browser(driver);
        localizedLogger.info("loc.browser.ready", browserProfile.getBrowserName().toString());
        return browser;
    }
}
