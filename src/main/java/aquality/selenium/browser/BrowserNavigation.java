package aquality.selenium.browser;

import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

class BrowserNavigation implements Navigation {

    private final RemoteWebDriver driver;

    BrowserNavigation(RemoteWebDriver driver){
        this.driver = driver;
    }

    @Override
    public void back() {
        infoLoc("loc.browser.back");
        getDriver().navigate().back();
    }

    @Override
    public void forward() {
        infoLoc("loc.browser.forward");
        getDriver().navigate().forward();
    }

    @Override
    public void to(String s) {
        infoLoc("loc.browser.navigate", s);
        getDriver().navigate().to(s);
    }

    @Override
    public void to(URL url) {
        infoLoc("loc.browser.navigate", url);
        getDriver().navigate().to(url);
    }

    @Override
    public void refresh() {
        infoLoc("loc.browser.refresh");
        getDriver().navigate().refresh();
    }

    private RemoteWebDriver getDriver() {
        return driver;
    }

    private void infoLoc(String key, Object... args) {
        AqualityServices.getLocalizedLogger().info(key, args);
    }
}
