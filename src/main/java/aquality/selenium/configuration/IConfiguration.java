package aquality.selenium.configuration;

/**
 * Describes tool configuration.
 */
public interface IConfiguration {

    /**
     * Gets desired browser profile.
     * @return Profile of browser.
     */
    IBrowserProfile getBrowserProfile();

    /**
     * Gets configuration of timeouts.
     * @return Configuration of timeouts.
     */
    ITimeoutConfiguration getTimeoutConfiguration();

    /**
     * Gets configuration of retries.
     * @return Configuration of retries.
     */
    IRetryConfiguration getRetryConfiguration();

    /**
     * Gets configuration of logger.
     * @return Configuration of logger.
     */
    ILoggerConfiguration getLoggerConfiguration();
}