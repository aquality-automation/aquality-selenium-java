package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

public class SafariSettings extends DriverSettings {

    public SafariSettings(ISettingsFile settingsFile) {
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        SafariOptions safariOptions = new SafariOptions();
        setCapabilities(safariOptions);
        return safariOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new IllegalArgumentException("Download directory for Safari profiles is not supported in capabilities. Please, use separate 'downloadDir' property");
    }

    @Override
    public String getDownloadDir() {
        return String.valueOf(getSettingsFile().getValue(getDriverSettingsPath("downloadDir")));
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.SAFARI;
    }
}
