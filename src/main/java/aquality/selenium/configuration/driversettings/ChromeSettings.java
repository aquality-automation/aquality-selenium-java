package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.utils.JsonFile;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeSettings extends DriverSettings {

    private final JsonFile jsonFile;

    public ChromeSettings(JsonFile jsonFile){
        this.jsonFile = jsonFile;
    }

    @Override
    public ChromeOptions getCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        setChromePrefs(chromeOptions);
        setCapabilities(chromeOptions, getBrowserName());
        setChromeArgs(chromeOptions);
        return chromeOptions;
    }

    private void setChromePrefs(ChromeOptions options){
        HashMap<String, Object> chromePrefs = new HashMap<>();
        Map<String, Object> configOptions = getBrowserOptions(getBrowserName());
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
        for (String arg : getBrowserStartArguments(getBrowserName())) {
            options.addArguments(arg);
        }
    }

    @Override
    public String getDownloadDirCapabilityKey() {
        return "download.default_directory";
    }

    @Override
    public JsonFile getSettingsFile() {
        return jsonFile;
    }

    @Override
    public BrowserName getBrowserName() {
        return BrowserName.CHROME;
    }
}
