package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.BrowserName;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import org.openqa.selenium.MutableCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

abstract class DriverSettings implements IDriverSettings {

    abstract ISettingsFile getSettingsFile();

    Map<String, Object> getBrowserOptions() {
        return getSettingsFile().getMap(getDriverSettingsPath(getBrowserName(), CapabilityType.OPTIONS));
    }

    private Map<String, Object> getBrowserCapabilities() {
        return getSettingsFile().getMap(getDriverSettingsPath(getBrowserName(), CapabilityType.CAPABILITIES));
    }

    List<String> getBrowserStartArguments() {
        return getSettingsFile().getList(getDriverSettingsPath(getBrowserName(), CapabilityType.START_ARGS));
    }

    void logStartArguments() {
        List<String> startArguments = getBrowserStartArguments();
        if (!startArguments.isEmpty()) {
            AqualityServices.getLocalizedLogger().info("loc.browser.arguments.setting", String.join(" ", startArguments));
        }
    }

    @Override
    public String getWebDriverVersion() {
        return String.valueOf(getSettingsFile().getValueOrDefault(
                getDriverSettingsPath(getBrowserName()) + "/webDriverVersion", "Latest"));
    }

    @Override
    public String getSystemArchitecture() {
        return String.valueOf(getSettingsFile().getValueOrDefault(
                getDriverSettingsPath(getBrowserName()) + "/systemArchitecture", "Auto"));
    }

    private String getDriverSettingsPath(BrowserName browserName, CapabilityType capabilityType){
        return getDriverSettingsPath(getBrowserName()) + "/" + capabilityType.getKey();
    }

    String getDriverSettingsPath(BrowserName browserName){
        return String.format("/driverSettings/%1$s", browserName.toString().toLowerCase());
    }

    void setCapabilities(MutableCapabilities options){
        Map<String, Object> capabilities = getBrowserCapabilities();
        capabilities.forEach(options::setCapability);
    }

    @Override
    public String getDownloadDir() {
        Map<String, Object> options = getBrowserOptions();
        String key = getDownloadDirCapabilityKey();

        if(options.containsKey(key)){
            String pathInConfiguration = String.valueOf(options.get(key));
            return pathInConfiguration.contains(".") ? getAbsolutePath(pathInConfiguration) : pathInConfiguration;
        }
        throw new IllegalArgumentException(String.format("failed to find %s profiles option for %s", key, getBrowserName()));
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
            String message = String.format(AqualityServices.get(ILocalizationManager.class).getLocalizedMessage("loc.file.reading_exception"), path);
            getLogger().fatal(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    private Logger getLogger(){
        return Logger.getInstance();
    }
}
