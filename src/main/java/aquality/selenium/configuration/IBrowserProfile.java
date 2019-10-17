package aquality.selenium.configuration;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.configuration.driversettings.IDriverSettings;

import java.net.URL;

/**
 * Describes browser settings.
 */
public interface IBrowserProfile {

    /**
     * Gets name of target browser.
     * @return Browser name.
     */
    BrowserName getBrowserName();

    /**
     * Checks if is remote browser or not.
     * @return true if remote browser and false if local.
     */
    boolean isRemote();

    /**
     * Is element hightlight enabled or not.
     * @return true if element highlight is enabled and false otherwise.
     */
    boolean isElementHighlightEnabled();

    /**
     * Gets driver settings for target browser.
     * @return Driver settings.
     */
    IDriverSettings getDriverSettings();

    /**
     * Gets remote connection URI is case of remote browser.
     * @return Remote connection URI.
     */
    URL getRemoteConnectionUrl();
}
