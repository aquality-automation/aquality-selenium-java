package aquality.selenium.configuration;

import java.time.Duration;

/**
 * Describes timeouts configuration.
 */
public interface ITimeoutConfiguration extends aquality.selenium.core.configurations.ITimeoutConfiguration {

    /**
     * Gets WedDriver AsynchronousJavaScript timeout.
     *
     * @return AsynchronousJavaScript timeout.
     */
    Duration getScript();

    /**
     * Gets WedDriver PageLoad timeout.
     *
     * @return PageLoad timeout.
     */
    Duration getPageLoad();
}
