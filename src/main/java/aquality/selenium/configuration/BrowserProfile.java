package aquality.selenium.configuration;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.configuration.driversettings.*;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.InvalidArgumentException;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserProfile implements IBrowserProfile {

   private final JsonFile settingsFile;

    public BrowserProfile(JsonFile settingsFile) {
        this.settingsFile = settingsFile;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.valueOf(String.valueOf(settingsFile.getValue("/browserName")).toUpperCase());
    }

    @Override
    public boolean isRemote() {
        return (Boolean) settingsFile.getValue("/isRemote");
    }

    @Override
    public boolean isElementHighlightEnabled() {
        return (Boolean) (settingsFile.getValue("/isElementHighlightEnabled"));
    }

    @Override
    public IDriverSettings getDriverSettings() {
        IDriverSettings driverSettings;
        switch (getBrowserName()){
            case CHROME:
                driverSettings = new ChromeSettings(settingsFile);
                break;
            case FIREFOX:
                driverSettings = new FirefoxSettings(settingsFile);
                break;
            case EDGE:
                driverSettings = new EdgeSettings(settingsFile);
                break;
            case IEXPLORER:
                driverSettings = new IExplorerSettings(settingsFile);
                break;
            case SAFARI:
                driverSettings = new SafariSettings(settingsFile);
                break;
            default:
                throw new IllegalArgumentException("There are no assigned behaviour for retrieving driver driversettings for browser " + getBrowserName());
        }
        return driverSettings;
    }

    @Override
    public URL getRemoteConnectionUrl() {
        String key = "remoteConnectionUrl";
        try {
            return new URL(String.valueOf(settingsFile.getValue("/" + key)));
        } catch (MalformedURLException e) {
            throw new InvalidArgumentException(String.format("Key %1$s was not found in file %2$s", key, settingsFile.getFileCanonicalPath()));
        }
    }
}
