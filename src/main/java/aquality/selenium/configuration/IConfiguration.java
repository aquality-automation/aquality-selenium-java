package aquality.selenium.configuration;

import aquality.selenium.core.configurations.IElementCacheConfiguration;
import aquality.selenium.core.configurations.ILoggerConfiguration;
import aquality.selenium.core.configurations.IRetryConfiguration;
import aquality.selenium.core.configurations.IVisualizationConfiguration;

/**
 * Describes tool configuration.
 */
public interface IConfiguration {

    /**
     * Gets desired browser profile.
     *
     * @return Profile of browser.
     */
    IBrowserProfile getBrowserProfile();

    /**
     * Gets configuration of timeouts.
     *
     * @return Configuration of timeouts.
     */
    ITimeoutConfiguration getTimeoutConfiguration();

    /**
     * Gets configuration of retries.
     *
     * @return Configuration of retries.
     */
    IRetryConfiguration getRetryConfiguration();

    /**
     * Gets configuration of logger.
     *
     * @return Configuration of logger.
     */
    ILoggerConfiguration getLoggerConfiguration();

    /**
     * Gets configuration of element caching.
     *
     * @return Configuration of element caching.
     */
    IElementCacheConfiguration getElementCacheConfiguration();

    /**
     * Gets configuration of VisualStateProvider and Dump manager.
     *
     * @return Visualization configuration.
     */
    IVisualizationConfiguration getVisualizationConfiguration();
}