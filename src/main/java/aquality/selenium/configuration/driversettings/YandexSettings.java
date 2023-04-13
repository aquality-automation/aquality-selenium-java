package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class YandexSettings extends ChromeSettings {
    private static final String DEFAULT_BINARY_LOCATION = "%USERPROFILE%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
    public YandexSettings(ISettingsFile settingsFile) {
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        ChromeOptions options = (ChromeOptions) super.getDriverOptions();
        options.setBinary(getBinaryLocation(DEFAULT_BINARY_LOCATION));
        return options;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.YANDEX;
    }
}
