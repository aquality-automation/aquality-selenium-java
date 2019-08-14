package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.safari.SafariOptions;

public class SafariSettings extends DriverSettings {

    private final JsonFile jsonFile;

    public SafariSettings(JsonFile jsonFile){
        this.jsonFile = jsonFile;
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
    public JsonFile getSettingsFile() {
        return jsonFile;
    }
}
