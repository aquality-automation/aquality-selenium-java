package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class IExplorerSettings extends DriverSettings {

    public IExplorerSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public AbstractDriverOptions<?> getDriverOptions() {
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        setCapabilities(internetExplorerOptions);
        internetExplorerOptions.setPageLoadStrategy(getPageLoadStrategy());
        internetExplorerOptions.addCommandSwitches(getBrowserStartArguments().toArray(new String[0]));
        return internetExplorerOptions;
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        throw new UnsupportedOperationException("Download directory for Internet Explorer profiles is not supported");
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.IEXPLORER;
    }
}
