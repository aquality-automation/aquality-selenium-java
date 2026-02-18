package aquality.selenium.configuration.driversettings;

import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.HashMap;
import java.util.Map;

public abstract class ChromiumSettings extends DriverSettings {

    private static final String MOBILE_EMULATION_CAPABILITY_KEY = "mobileEmulation";
    private static final String DEVICE_METRICS_CAPABILITY_KEY = "deviceMetrics";
    private static final String CLIENT_HINTS_CAPABILITY_KEY = "clientHints";

    public ChromiumSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    protected <T extends ChromiumOptions<T>> void setupDriverOptions(T options) {
        setPrefs(options);
        setCapabilities(options);
        getBrowserStartArguments().forEach(options::addArguments);
        setExcludedArguments(options);
        options.setPageLoadStrategy(getPageLoadStrategy());
    }

    void setLoggingPreferences(MutableCapabilities options, String capabilityKey) {
        if (!getLoggingPreferences().isEmpty()) {
            LoggingPreferences logs = new LoggingPreferences();
            getLoggingPreferences().forEach(logs::enable);
            options.setCapability(capabilityKey, logs);
        }
    }

    @Override
    void setCapabilities(MutableCapabilities options) {
        getBrowserCapabilities().forEach((key, value) -> {
            if (key.equals(MOBILE_EMULATION_CAPABILITY_KEY)) {
                Map<String, Object> mobileOptions = getMapOrEmpty(getDriverSettingsPath(CapabilityType.CAPABILITIES.getKey(), MOBILE_EMULATION_CAPABILITY_KEY));
                if (mobileOptions.containsKey(DEVICE_METRICS_CAPABILITY_KEY)) {
                    Map<String, Object> deviceMetrics = getMapOrEmpty(getDriverSettingsPath(CapabilityType.CAPABILITIES.getKey(), MOBILE_EMULATION_CAPABILITY_KEY, DEVICE_METRICS_CAPABILITY_KEY));
                    mobileOptions.put(DEVICE_METRICS_CAPABILITY_KEY, deviceMetrics);
                }
                if (mobileOptions.containsKey(CLIENT_HINTS_CAPABILITY_KEY)) {
                    Map<String, Object> clientHints = getMapOrEmpty(getDriverSettingsPath(CapabilityType.CAPABILITIES.getKey(), MOBILE_EMULATION_CAPABILITY_KEY, CLIENT_HINTS_CAPABILITY_KEY));
                    mobileOptions.put(CLIENT_HINTS_CAPABILITY_KEY, clientHints);
                }
                ((ChromiumOptions<?>)options).setExperimentalOption(key, mobileOptions);
            } else {
                options.setCapability(key, value);
            }
        });
    }

    protected <T extends ChromiumOptions<T>> void setPrefs(T options){
        HashMap<String, Object> prefs = new HashMap<>();
        Map<String, Object> configOptions = getBrowserOptions();
        configOptions.forEach((key, value) -> {
            if (key.equals(getDownloadDirCapabilityKey())) {
                prefs.put(key, getDownloadDir());
            } else {
                prefs.put(key, value);
            }
        });
        options.setExperimentalOption("prefs", prefs);
    }


    protected <T extends ChromiumOptions<T>> void setExcludedArguments(T chromiumOptions) {
        chromiumOptions.setExperimentalOption("excludeSwitches", getExcludedArguments());
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        return "download.default_directory";
    }
}
