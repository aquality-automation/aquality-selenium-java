package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IActionRetrier;
import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class LocalBrowserFactory extends BrowserFactory {

    private final IBrowserProfile browserProfile;

    public LocalBrowserFactory(IActionRetrier actionRetrier, IBrowserProfile browserProfile, ILocalizedLogger localizedLogger) {
        super(actionRetrier, browserProfile, localizedLogger);
        this.browserProfile = browserProfile;
    }

    @Override
    public RemoteWebDriver getDriver() {
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
                throw new IllegalArgumentException(String.format("Browser [%s] is not supported.", browserName));
        }
        return driver;
    }

    private  <T extends RemoteWebDriver> T getDriver(Class<T> driverClass, Capabilities capabilities) {
        try {
            return driverClass.getDeclaredConstructor(Capabilities.class).newInstance(capabilities);
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException(String.format("Cannot instantiate driver with type '%1$s'.", driverClass), e);
        }
    }
}
