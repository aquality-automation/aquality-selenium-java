package aquality.selenium.configuration;

import aquality.selenium.core.utilities.ISettingsFile;
import com.google.inject.Inject;

import java.time.Duration;

public class TimeoutConfiguration extends aquality.selenium.core.configurations.TimeoutConfiguration
        implements ITimeoutConfiguration {

    private final ISettingsFile settingsFile;
    private final Duration script;
    private final Duration pageLoad;

    @Inject
    public TimeoutConfiguration(ISettingsFile settingsFile) {
        super(settingsFile);
        this.settingsFile = settingsFile;
        script = getDurationFromSeconds(TIMEOUT.SCRIPT);
        pageLoad = getDurationFromSeconds(TIMEOUT.PAGE_LOAD);
    }

    private Duration getDurationFromSeconds(TIMEOUT timeout) {
        long seconds = Long.valueOf(settingsFile.getValue("/timeouts/" + timeout.getKey()).toString());
        return Duration.ofSeconds(seconds);
    }

    public Duration getScript() {
        return script;
    }

    public Duration getPageLoad() {
        return pageLoad;
    }

    private enum TIMEOUT {
        SCRIPT("timeoutScript"),
        PAGE_LOAD("timeoutPageLoad");

        private String key;

        TIMEOUT(String key) {
            this.key = key;
        }

        private String getKey() {
            return key;
        }
    }
}
