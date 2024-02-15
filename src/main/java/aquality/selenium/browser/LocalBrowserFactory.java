package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IActionRetrier;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class LocalBrowserFactory extends BrowserFactory {

    private final IBrowserProfile browserProfile;

    public LocalBrowserFactory(IActionRetrier actionRetrier, IBrowserProfile browserProfile, ILocalizedLogger localizedLogger) {
        super(actionRetrier, browserProfile, localizedLogger);
        this.browserProfile = browserProfile;
    }

    @Override
    protected RemoteWebDriver getDriver() {
        BrowserName browserName = browserProfile.getBrowserName();
        RemoteWebDriver driver;
        IDriverSettings driverSettings = browserProfile.getDriverSettings();
        switch (browserName) {
            case CHROME:
                driver = new ChromeDriver((ChromeOptions) driverSettings.getDriverOptions());
                break;
            case YANDEX:
            case OPERA:
                driver = new ChromeDriver(new ChromeDriverService.Builder().withBuildCheckDisabled(true).build(),
                        (ChromeOptions) driverSettings.getDriverOptions());
                break;
            case FIREFOX:
                driver = new FirefoxDriver((FirefoxOptions) driverSettings.getDriverOptions());
                break;
            case IEXPLORER:
                driver = new InternetExplorerDriver((InternetExplorerOptions) driverSettings.getDriverOptions());
                break;
            case EDGE:
                driver = new EdgeDriver((EdgeOptions) driverSettings.getDriverOptions());
                break;
            case SAFARI:
                driver = new SafariDriver((SafariOptions) driverSettings.getDriverOptions());
                break;
            default:
                throw new UnsupportedOperationException(String.format("Browser [%s] is not supported.", browserName));
        }
        return driver;
    }
}
