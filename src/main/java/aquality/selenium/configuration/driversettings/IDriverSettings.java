package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.remote.AbstractDriverOptions;

/**
 * Describes web driver settings.
 */
public interface IDriverSettings {

    /**
     * Gets web driver capabilities.
     * @return initialized {@link Capabilities}
     */
    AbstractDriverOptions<?> getDriverOptions();

    /**
     * Gets WebDriver page load strategy.
     * @return initialized {@link PageLoadStrategy}
     */
    PageLoadStrategy getPageLoadStrategy();

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
