package aquality.selenium.configuration.driversettings;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import io.github.bonigarcia.wdm.config.Architecture;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

abstract class DriverSettings implements IDriverSettings {

    private final ISettingsFile settingsFile;
    private Map<String, Object> options;
    private Map<String, Object> capabilities;
    private Map<String, Level> loggingPreferences;
    private List<String> startArguments;

    protected DriverSettings(ISettingsFile settingsFile) {
        this.settingsFile = settingsFile;
    }

    protected ISettingsFile getSettingsFile() {
        return settingsFile;
    }

    protected Map<String, Object> getBrowserOptions() {
        if (options == null) {
            options = getMapOrEmpty(CapabilityType.OPTIONS);
        }
        return options;
    }

    protected Map<String, Object> getBrowserCapabilities() {
        if (capabilities == null) {
            capabilities = getMapOrEmpty(CapabilityType.CAPABILITIES);
        }
        return capabilities;
    }

    protected Map<String, Level> getLoggingPreferences() {
        if (loggingPreferences == null) {
            loggingPreferences = getMapOrEmpty(CapabilityType.LOGGING_PREFERENCES).entrySet().stream().collect(
                    Collectors.toMap(entry -> entry.getKey().toLowerCase(),
                            pair -> Level.parse(pair.getValue().toString().toUpperCase())));
        }
        return loggingPreferences;
    }

    private Map<String, Object> getMapOrEmpty(CapabilityType capabilityType) {
        String path = getDriverSettingsPath(capabilityType);
        Map<String, Object> map = getSettingsFile().isValuePresent(path) ? getSettingsFile().getMap(path) : Collections.emptyMap();
        logCollection("loc.browser.".concat(capabilityType.getKey()), map);
        return map;
    }

    protected List<String> getBrowserStartArguments() {
        if (startArguments == null) {
            String path = getDriverSettingsPath(CapabilityType.START_ARGS);
            boolean isValuePresent;
            try {
                getSettingsFile().getValue(path);
                isValuePresent = true;
            }
            catch (IllegalArgumentException e) {
                isValuePresent = false;
            }
            startArguments = isValuePresent ? getSettingsFile().getList(path) : Collections.emptyList();
            logCollection("loc.browser.arguments", startArguments);
        }
        return startArguments;
    }

    protected String getBinaryLocation(String defaultBinaryLocation) {
        String value = (String) getSettingsFile().getValueOrDefault(getDriverSettingsPath("binaryLocation"), defaultBinaryLocation);
        int varStartIndex = value.indexOf('%');
        int varEndIndex = value.lastIndexOf('%');
        return varStartIndex == 0 && varStartIndex != varEndIndex
                ? System.getenv(value.substring(varStartIndex + 1, varEndIndex)) + value.substring(varEndIndex + 1)
                : getAbsolutePath(value);
    }

    @SafeVarargs
    private final <T> void logCollection(String messageKey, final T... elements) {
        if (elements.length == 1 &&
                ((elements[0] instanceof Map && !((Map)elements[0]).isEmpty())
                || (elements[0] instanceof List && !((List)elements[0]).isEmpty()))) {
            AqualityServices.getLocalizedLogger()
                    .debug(messageKey,System.lineSeparator() + StringUtils.join(elements));
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
        getBrowserCapabilities().forEach(options::setCapability);
    }

    void setLoggingPreferences(MutableCapabilities options, String capabilityKey) {
        if (!getLoggingPreferences().isEmpty()) {
            LoggingPreferences logs = new LoggingPreferences();
            getLoggingPreferences().forEach(logs::enable);
            options.setCapability(capabilityKey, logs);
        }
    }

    @Override
    public String getDownloadDir() {
        Map<String, Object> browserOptions = getBrowserOptions();
        String key = getDownloadDirCapabilityKey();

        if (browserOptions.containsKey(key)) {
            String pathInConfiguration = String.valueOf(browserOptions.get(key));
            return pathInConfiguration.contains(".") ? getAbsolutePath(pathInConfiguration) : pathInConfiguration;
        }
        throw new IllegalArgumentException(String.format("failed to find %s profiles option for %s", key, getBrowserName()));
    }

    private enum CapabilityType {
        CAPABILITIES("capabilities"),
        OPTIONS("options"),
        START_ARGS("startArguments"),
        LOGGING_PREFERENCES("loggingPreferences");

        private final String key;

        CapabilityType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    protected String getAbsolutePath(String path) {
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
