package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class OperaSettings extends ChromeSettings {
    private static final String DEFAULT_BINARY_LOCATION = "%USERPROFILE%\\AppData\\Local\\Programs\\Opera\\launcher.exe";
    public OperaSettings(ISettingsFile settingsFile) {
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        ChromeOptions options = (ChromeOptions) super.getDriverOptions();
        options.setExperimentalOption("w3c", true);
        options.setBinary(getBinaryLocation(DEFAULT_BINARY_LOCATION));
        return options;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.OPERA;
    }
}
