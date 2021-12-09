package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeSettings extends DriverSettings {

    public EdgeSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public EdgeOptions getCapabilities() {
        EdgeOptions edgeOptions = new EdgeOptions();
        setCapabilities(edgeOptions);
        edgeOptions.setPageLoadStrategy(getPageLoadStrategy());
        return edgeOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new IllegalArgumentException("Download directory for EDGE profiles is not supported");
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.EDGE;
    }
}
