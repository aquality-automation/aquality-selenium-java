package aquality.selenium.configuration;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.configuration.driversettings.IDriverSettings;

import java.net.URL;

public interface IBrowserProfile {

    BrowserName getBrowserName();

    boolean isRemote();

    boolean isElementHighlightEnabled();

    IDriverSettings getDriverSettings();

    URL getRemoteConnectionUrl();
}
