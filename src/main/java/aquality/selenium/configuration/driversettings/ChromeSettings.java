package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeSettings extends DriverSettings {

    public ChromeSettings(ISettingsFile settingsFile){
        super(settingsFile);
    }

    @Override
    public ChromeOptions getCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        setChromePrefs(chromeOptions);
        setCapabilities(chromeOptions);
        setChromeArgs(chromeOptions);
        chromeOptions.setPageLoadStrategy(getPageLoadStrategy());
        return chromeOptions;
    }

    private void setChromePrefs(ChromeOptions options){
        HashMap<String, Object> chromePrefs = new HashMap<>();
        Map<String, Object> configOptions = getBrowserOptions();
        configOptions.forEach((key, value) -> {
            if (key.equals(getDownloadDirCapabilityKey())) {
                chromePrefs.put(key, getDownloadDir());
            } else {
                chromePrefs.put(key, value);
            }
        });
        options.setExperimentalOption("prefs", chromePrefs);
    }

    private void setChromeArgs(ChromeOptions options) {
        logStartArguments();
        for (String arg : getBrowserStartArguments()) {
            options.addArguments(arg);
        }
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        return "download.default_directory";
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.CHROME;
    }
}
