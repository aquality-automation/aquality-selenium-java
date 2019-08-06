package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeSettings extends DriverSettings {

    private final JsonFile jsonFile;

    public EdgeSettings(JsonFile jsonFile){
        this.jsonFile = jsonFile;
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
    public JsonFile getSettingsFile() {
        return jsonFile;
    }
}
