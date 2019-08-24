package aquality.selenium.browser;

import aquality.selenium.configuration.IConfiguration;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;


public class RemoteBrowserFactory extends BrowserFactory {

    private final IConfiguration configuration;

    public RemoteBrowserFactory(IConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public Browser getBrowser(){
        BrowserName browserName = getBrowserName();
        IDriverSettings driverSettings = configuration.getBrowserProfile().getDriverSettings();
        logBrowserIsReady(browserName);
        RemoteWebDriver driver = createRemoteDriver(driverSettings.getCapabilities());
        return new Browser(driver, configuration);
    }

    private BrowserName getBrowserName() {
        return configuration.getBrowserProfile().getBrowserName();
    }

    private RemoteWebDriver createRemoteDriver(Capabilities capabilities){
        Logger logger = Logger.getInstance();
        logger.info(LocalizationManager.getInstance().getValue("loc.browser.grid"));

        RemoteWebDriver driver = new RemoteWebDriver(
                configuration.getBrowserProfile().getRemoteConnectionUrl(), capabilities);

        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }
}
