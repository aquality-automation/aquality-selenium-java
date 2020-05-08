package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public class FirefoxSettings extends DriverSettings {

    public FirefoxSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public FirefoxOptions getCapabilities() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        setCapabilities(firefoxOptions);
        setFirefoxPrefs(firefoxOptions);
        setFirefoxArgs(firefoxOptions);
        firefoxOptions.setPageLoadStrategy(getPageLoadStrategy());
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

    private void setFirefoxPrefs(FirefoxOptions options) {
        Map<String, Object> configOptions = getBrowserOptions();
        configOptions.forEach((key, value) -> {
            if (key.equals(getDownloadDirCapabilityKey())) {
                options.addPreference(key, getDownloadDir());
            } else if(value instanceof Boolean) {
                options.addPreference(key, (boolean) value);
            } else if (value instanceof Integer) {
                options.addPreference(key, (int) value);
            } else if (value instanceof String) {
                options.addPreference(key, (String) value);
            }
        });
    }

    private void setFirefoxArgs(FirefoxOptions options) {
        logStartArguments();
        for (String arg : getBrowserStartArguments()) {
            options.addArguments(arg);
        }
    }
}
