package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class IExplorerSettings extends DriverSettings {

    private final ISettingsFile settingsFile;

    public IExplorerSettings(ISettingsFile settingsFile){
        this.settingsFile = settingsFile;
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
    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }
}
