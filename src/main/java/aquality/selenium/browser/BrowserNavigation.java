package aquality.selenium.browser;

import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Browser navigation wrapper with localized logging.
 */
public class BrowserNavigation implements Navigation {

    private final RemoteWebDriver driver;
    private final ILocalizedLogger logger;

    protected BrowserNavigation(RemoteWebDriver driver, ILocalizedLogger logger){
        this.driver = driver;
        this.logger = logger;
    }

    @Override
    public void back() {
        infoLoc("loc.browser.back");
        driver.navigate().back();
    }

    @Override
    public void forward() {
        infoLoc("loc.browser.forward");
        driver.navigate().forward();
    }

    @Override
    public void to(String s) {
        infoLoc("loc.browser.navigate", s);
        driver.navigate().to(s);
    }

    @Override
    public void to(URL url) {
        infoLoc("loc.browser.navigate", url);
        driver.navigate().to(url);
    }

    @Override
    public void refresh() {
        infoLoc("loc.browser.refresh");
        driver.navigate().refresh();
    }

    private void infoLoc(String key, Object... args) {
        logger.info(key, args);
    }
}
