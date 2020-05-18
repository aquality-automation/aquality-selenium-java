package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class LocalBrowserFactory implements BrowserFactory {

    private final IBrowserProfile browserProfile;

    public LocalBrowserFactory() {
        this.browserProfile = AqualityServices.getBrowserProfile();
    }

    @Override
    public Browser getBrowser() {
        BrowserName browserName = browserProfile.getBrowserName();
        RemoteWebDriver driver;
        IDriverSettings driverSettings = browserProfile.getDriverSettings();
        String webDriverVersion = driverSettings.getWebDriverVersion();
        Architecture systemArchitecture = driverSettings.getSystemArchitecture();
        switch (browserName) {
            case CHROME:
                WebDriverManager.chromedriver().version(webDriverVersion).setup();
                driver = getDriver(ChromeDriver.class, driverSettings.getCapabilities());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().version(webDriverVersion).setup();
                driver = getDriver(FirefoxDriver.class, driverSettings.getCapabilities());
                break;
            case IEXPLORER:
                WebDriverManager.iedriver().architecture(systemArchitecture).version(webDriverVersion).setup();
                driver = getDriver(InternetExplorerDriver.class, driverSettings.getCapabilities());
                break;
            case EDGE:
                WebDriverManager.edgedriver().version(webDriverVersion).setup();
                driver = getDriver(EdgeDriver.class, driverSettings.getCapabilities());
                break;
            case SAFARI:
                driver = getDriver(SafariDriver.class, driverSettings.getCapabilities());
                break;
            default:
                throw getLoggedWrongBrowserNameException();
        }
        logBrowserIsReady(browserName);

        return new Browser(driver);
    }
}
