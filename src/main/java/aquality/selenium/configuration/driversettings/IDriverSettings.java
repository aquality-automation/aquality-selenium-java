package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import org.openqa.selenium.Capabilities;

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
     * Gets version of web driver for WebDriverManager.
     * @return Version of web driver.
     */
    String getWebDriverVersion();

    /**
     * Gets target system architecture for WebDriverManager.
     * @return System architecture.
     */
    String getSystemArchitecture();

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
