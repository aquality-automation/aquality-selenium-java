package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class ChromeSettings extends ChromiumSettings {

    public ChromeSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        setupDriverOptions(chromeOptions);
        setLoggingPreferences(chromeOptions, ChromeOptions.LOGGING_PREFS);
        return chromeOptions;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.CHROME;
    }
}
