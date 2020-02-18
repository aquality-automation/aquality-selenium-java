package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.safari.SafariOptions;

public class SafariSettings extends DriverSettings {

    private final ISettingsFile settingsFile;

    public SafariSettings(ISettingsFile settingsFile){
        this.settingsFile = settingsFile;
    }

    @Override
    public SafariOptions getCapabilities() {
        SafariOptions safariOptions = new SafariOptions();
        setCapabilities(safariOptions, getBrowserName());
        return safariOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new IllegalArgumentException("Download directory for Safari profiles is not supported in capabilities. Please, use separate 'downloadDir' property");
    }

    @Override
    String getDownloadDirectory(BrowserName browserName) {
        return String.valueOf(getSettingsFile().getValue(getDriverSettingsPath(getBrowserName()) + "/downloadDir"));
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.SAFARI;
    }

    @Override
    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }
}
