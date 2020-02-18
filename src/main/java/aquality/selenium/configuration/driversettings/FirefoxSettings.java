package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public class FirefoxSettings extends DriverSettings {

    private final ISettingsFile settingsFile;

    public FirefoxSettings(ISettingsFile settingsFile){
        this.settingsFile = settingsFile;
    }

    @Override
    public FirefoxOptions getCapabilities() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        setCapabilities(firefoxOptions, getBrowserName());
        setFirefoxPrefs(firefoxOptions);
        setFirefoxArgs(firefoxOptions);
        return firefoxOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        return "browser.download.dir";
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.FIREFOX;
    }

    @Override
    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }

    private void setFirefoxPrefs(FirefoxOptions options) {
        Map<String, Object> configOptions = getBrowserOptions(getBrowserName());
        configOptions.forEach((key, value) -> {
            if (key.equals(getDownloadDirCapabilityKey())) {
                options.addPreference(key, getDownloadDir());
            }else if(value instanceof Boolean){
                options.addPreference(key, (Boolean) value);
            }
            else if (value instanceof Integer) {
                options.addPreference(key, Integer.valueOf(value.toString()));
            } else if (value instanceof String) {
                options.addPreference(key, (String) value);
            }
        });
    }

    private void setFirefoxArgs(FirefoxOptions options) {
        for (String arg : getBrowserStartArguments(getBrowserName())) {
            options.addArguments(arg);
        }
    }
}
