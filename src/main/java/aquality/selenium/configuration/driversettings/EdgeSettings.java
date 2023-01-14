package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

import java.util.HashMap;
import java.util.Map;

public class EdgeSettings extends DriverSettings {

    public EdgeSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        setCapabilities(edgeOptions);
        setPrefs(edgeOptions);
        getBrowserStartArguments().forEach(edgeOptions::addArguments);
        edgeOptions.setPageLoadStrategy(getPageLoadStrategy());
        setLoggingPreferences(edgeOptions, EdgeOptions.LOGGING_PREFS);
        return edgeOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        return "download.default_directory";
    }

    private void setPrefs(EdgeOptions options){
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

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.EDGE;
    }
}
