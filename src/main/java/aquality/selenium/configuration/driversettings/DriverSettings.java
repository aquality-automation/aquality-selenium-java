package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import io.github.bonigarcia.wdm.Architecture;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class DriverSettings implements IDriverSettings {

    private final ISettingsFile settingsFile;

    protected DriverSettings(ISettingsFile settingsFile) {
        this.settingsFile = settingsFile;
    }

    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }

    Map<String, Object> getBrowserOptions() {
        return getSettingsFile().getMap(getDriverSettingsPath(CapabilityType.OPTIONS));
    }

    private Map<String, Object> getBrowserCapabilities() {
        return getSettingsFile().getMap(getDriverSettingsPath(CapabilityType.CAPABILITIES));
    }

    List<String> getBrowserStartArguments() {
        return getSettingsFile().getList(getDriverSettingsPath(CapabilityType.START_ARGS));
    }

    void logStartArguments() {
        List<String> startArguments = getBrowserStartArguments();
        if (!startArguments.isEmpty()) {
            AqualityServices.getLocalizedLogger()
                    .info("loc.browser.arguments.setting", String.join(" ", startArguments));
        }
    }

    @Override
    public String getWebDriverVersion() {
        return String.valueOf(getSettingsFile().getValueOrDefault(
                getDriverSettingsPath("webDriverVersion"), "Latest"));
    }

    @Override
    public Architecture getSystemArchitecture() {
        String strValue = String.valueOf(getSettingsFile().getValueOrDefault(
                getDriverSettingsPath("systemArchitecture"), "Auto"));
        return Arrays.stream(Architecture.values())
                .filter(value -> value.name().equals(strValue))
                .findFirst()
                .orElse(Architecture.X32);
    }

    @Override
    public PageLoadStrategy getPageLoadStrategy() {
        String value = (String) getSettingsFile().getValueOrDefault(getDriverSettingsPath("pageLoadStrategy"), "normal");
        return PageLoadStrategy.fromString(value.toLowerCase());
    }

    private String getDriverSettingsPath(final CapabilityType capabilityType) {
        return getDriverSettingsPath(capabilityType.getKey());
    }

    String getDriverSettingsPath(final String... paths) {
        String pathToDriverSettings = String.format("/driverSettings/%1$s", getBrowserName().toString().toLowerCase());
        return pathToDriverSettings.concat(Arrays.stream(paths).map("/"::concat).collect(Collectors.joining()));
    }

    void setCapabilities(MutableCapabilities options) {
        Map<String, Object> capabilities = getBrowserCapabilities();
        capabilities.forEach(options::setCapability);
    }

    @Override
    public String getDownloadDir() {
        Map<String, Object> options = getBrowserOptions();
        String key = getDownloadDirCapabilityKey();

        if (options.containsKey(key)) {
            String pathInConfiguration = String.valueOf(options.get(key));
            return pathInConfiguration.contains(".") ? getAbsolutePath(pathInConfiguration) : pathInConfiguration;
        }
        throw new IllegalArgumentException(String.format("failed to find %s profiles option for %s", key, getBrowserName()));
    }

    private enum CapabilityType {
        CAPABILITIES("capabilities"), OPTIONS("options"), START_ARGS("startArguments");

        private String key;

        CapabilityType(String key) {
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
            String message = String.format(AqualityServices.get(ILocalizationManager.class)
                    .getLocalizedMessage("loc.file.reading_exception"), path);
            getLogger().fatal(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    private Logger getLogger() {
        return Logger.getInstance();
    }
}
