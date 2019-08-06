package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class IExplorerSettings extends DriverSettings {

    private final JsonFile jsonFile;

    public IExplorerSettings(JsonFile jsonFile){
        this.jsonFile = jsonFile;
    }

    @Override
    public InternetExplorerOptions getCapabilities() {
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        setCapabilities(internetExplorerOptions, getBrowserName());
        return internetExplorerOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new IllegalArgumentException("Download directory for Internet Explorer profiles is not supported");
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.IEXPLORER;
    }

    @Override
    public JsonFile getSettingsFile() {
        return jsonFile;
    }
}
