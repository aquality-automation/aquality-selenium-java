package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class IExplorerSettings extends DriverSettings {

    public IExplorerSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public InternetExplorerOptions getCapabilities() {
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        setCapabilities(internetExplorerOptions);
        internetExplorerOptions.setPageLoadStrategy(getPageLoadStrategy());
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
}
