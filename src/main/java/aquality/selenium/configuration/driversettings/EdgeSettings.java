package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class EdgeSettings extends ChromiumSettings {

    public EdgeSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        setupDriverOptions(edgeOptions);
        setLoggingPreferences(edgeOptions, EdgeOptions.LOGGING_PREFS);
        return edgeOptions;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.EDGE;
    }
}
