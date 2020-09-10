package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import io.github.bonigarcia.wdm.config.Architecture;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;

/**
 * Describes web driver settings.
 */
public interface IDriverSettings {

    /**
     * Gets web driver capabilities.
     * @return initialized {@link Capabilities}
     */
    Capabilities getCapabilities();

    /**
     * Gets WebDriver page load strategy.
     * @return initialized {@link PageLoadStrategy}
     */
    PageLoadStrategy getPageLoadStrategy();

    /**
     * Gets version of web driver for WebDriverManager.
     * @return Version of web driver.
     */
    String getWebDriverVersion();

    /**
     * Gets target system architecture for WebDriverManager.
     * @return initialized {@link Architecture}.
     */
    Architecture getSystemArchitecture();

    /**
     * Gets download directory for web driver.
     * @return Path to download directory.
     */
    String getDownloadDir();

    /**
     * Gets web driver capability key for download directory.
     * @return Web driver capability key
     */
    String getDownloadDirCapabilityKey();

    /**
     * Get desired browser name.
     * @return Browser name
     */
    BrowserName getBrowserName();
}
