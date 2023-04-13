package aquality.selenium.logging;

/**
 * Logging parameters for specific features, such as HTTP Exchange.
 */
public class LoggingParameters {
    private final boolean enabled;
    private final LogLevel logLevel;

    /**
     * Initializes logging parameters.
     * @param enabled enabled or not.
     * @param logLevel expected log level.
     */
    public LoggingParameters(boolean enabled, LogLevel logLevel) {

        this.enabled = enabled;
        this.logLevel = logLevel;
    }

    /**
     * Logging level for the specific feature.
     * @return logging level for the specific feature.
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Whether feature logging should be enabled or not.
     * @return true if the feature is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }
}
