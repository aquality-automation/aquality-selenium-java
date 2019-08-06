package aquality.selenium.browser;

import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

class BrowserNavigation implements Navigation {

    private final RemoteWebDriver driver;
    private final Logger logger = Logger.getInstance();

    BrowserNavigation(RemoteWebDriver driver){
        this.driver = driver;
    }

    @Override
    public void back() {
        logger.info(getLocManger().getValue("loc.browser.back"));
        getDriver().navigate().back();
    }

    @Override
    public void forward() {
        logger.info(getLocManger().getValue("loc.browser.forward"));
        getDriver().navigate().forward();
    }

    @Override
    public void to(String s) {
        logger.info(getLocManger().getValue("loc.browser.navigate"), s);
        getDriver().navigate().to(s);
    }

    @Override
    public void to(URL url) {
        logger.info(getLocManger().getValue("loc.browser.navigate"), url);
        getDriver().navigate().to(url);
    }

    @Override
    public void refresh() {
        logger.info(getLocManger().getValue("loc.browser.refresh"));
        getDriver().navigate().refresh();
    }

    private RemoteWebDriver getDriver() {
        return driver;
    }

    private LocalizationManager getLocManger(){
        return LocalizationManager.getInstance();
    }
}
