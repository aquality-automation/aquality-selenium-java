package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.Capabilities;

public interface IDriverSettings {

    Capabilities getCapabilities();

    String getWebDriverVersion();

    String getSystemArchitecture();

    String getDownloadDir();

    String getDownloadDirCapabilityKey();

    BrowserName getBrowserName();

    JsonFile getSettingsFile();

}
