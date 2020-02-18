package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.MutableCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

abstract class DriverSettings implements IDriverSettings {

    abstract ISettingsFile getSettingsFile();

    Map<String, Object> getBrowserOptions(BrowserName browserName) {
        return getSettingsFile().getMap(getDriverSettingsPath(browserName, CapabilityType.OPTIONS));
    }

    private Map<String, Object> getBrowserCapabilities(BrowserName browserName) {
        return getSettingsFile().getMap(getDriverSettingsPath(browserName, CapabilityType.CAPABILITIES));
    }

    List<String> getBrowserStartArguments(BrowserName browserName) {
        return getSettingsFile().getList(getDriverSettingsPath(browserName, CapabilityType.START_ARGS));
    }

    @Override
    public String getWebDriverVersion() {
        return String.valueOf(getSettingsFile().getValue(getDriverSettingsPath(getBrowserName()) + "/webDriverVersion"));
    }

    @Override
    public String getSystemArchitecture() {
        return String.valueOf(getSettingsFile().getValue(getDriverSettingsPath(getBrowserName()) + "/systemArchitecture"));
    }

    private String getDriverSettingsPath(BrowserName browserName, CapabilityType capabilityType){
        return getDriverSettingsPath(browserName) + "/" + capabilityType.getKey();
    }

    protected String getDriverSettingsPath(BrowserName browserName){
        return String.format("/driverSettings/%1$s", browserName.toString().toLowerCase());
    }

    void setCapabilities(MutableCapabilities options, BrowserName browserName){
        Map<String, Object> capabilities = getBrowserCapabilities(browserName);
        capabilities.forEach(options::setCapability);
    }

    public String getDownloadDir() {
        return getDownloadDirectory(getBrowserName());
    }

    String getDownloadDirectory(BrowserName browserName) {
        Map<String, Object> options = getBrowserOptions(browserName);
        String key = getDownloadDirCapabilityKey();

        if(options.containsKey(key)){
            String pathInConfiguration = String.valueOf(options.get(key));
            return pathInConfiguration.contains(".") ? getAbsolutePath(pathInConfiguration) : pathInConfiguration;
        }
        throw new IllegalArgumentException(String.format("failed to find %s profiles option for %s", key, browserName));
    }

    private enum CapabilityType {
        CAPABILITIES("capabilities"),OPTIONS("options"),START_ARGS("startArguments");

        private String key;
        CapabilityType(String key){
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    private String getAbsolutePath(String path) {
        try {
            return new File(path).getCanonicalPath();
        } catch (IOException e) {
            String message = String.format(LocalizationManager.getInstance().getValue("loc.file.reading_exception"), path);
            getLogger().fatal(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    private Logger getLogger(){
        return Logger.getInstance();
    }
}
