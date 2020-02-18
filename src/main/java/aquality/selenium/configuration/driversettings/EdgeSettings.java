package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeSettings extends DriverSettings {

    private final ISettingsFile settingsFile;

    public EdgeSettings(ISettingsFile settingsFile){
        this.settingsFile = settingsFile;
    }

    @Override
    public EdgeOptions getCapabilities() {
        EdgeOptions edgeOptions = new EdgeOptions();
        setCapabilities(edgeOptions, getBrowserName());
        return edgeOptions;
    }

    @Override
    public String getDownloadDir() {
        return getDownloadDirectory(getBrowserName());
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new IllegalArgumentException("Download directory for EDGE profiles is not supported");
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.EDGE;
    }

    @Override
    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }
}
