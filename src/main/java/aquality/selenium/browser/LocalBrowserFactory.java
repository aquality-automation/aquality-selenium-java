package aquality.selenium.browser;

import aquality.selenium.configuration.IBrowserProfile;
import aquality.selenium.configuration.driversettings.EdgeSettings;
import aquality.selenium.configuration.driversettings.IDriverSettings;
import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Arrays;

public class LocalBrowserFactory extends BrowserFactory {

    private final IBrowserProfile browserProfile;

    public LocalBrowserFactory(){
        this.browserProfile = AqualityServices.getBrowserProfile();
    }

    @Override
    public Browser getBrowser() {
        BrowserName browserName = browserProfile.getBrowserName();
        RemoteWebDriver driver;
        IDriverSettings driverSettings = browserProfile.getDriverSettings();
        String webDriverVersion = driverSettings.getWebDriverVersion();
        Architecture systemArchitecture = Arrays.stream(Architecture.values())
                .filter(value -> value.name().equals(driverSettings.getSystemArchitecture()))
                .findFirst()
                .orElse(Architecture.X32);
        switch (browserName) {
            case CHROME:
                WebDriverManager.chromedriver().version(webDriverVersion).setup();
                driver = new ChromeDriver((ChromeOptions) driverSettings.getCapabilities());
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().version(webDriverVersion).setup();
                driver = new FirefoxDriver((FirefoxOptions) driverSettings.getCapabilities());
                break;
            case IEXPLORER:
                WebDriverManager.iedriver().architecture(systemArchitecture).version(webDriverVersion).setup();
                driver = new InternetExplorerDriver((InternetExplorerOptions) driverSettings.getCapabilities());
                break;
            case EDGE:
                WebDriverManager.edgedriver().version(webDriverVersion).setup();
                driver = new EdgeDriver(((EdgeSettings) driverSettings).getCapabilities());
                break;
            case SAFARI:
                driver = new SafariDriver((SafariOptions) driverSettings.getCapabilities());
                break;
            default:
                throw getLoggedWrongBrowserNameException();
        }
        logBrowserIsReady(browserName);

        return new Browser(driver);
    }
}
