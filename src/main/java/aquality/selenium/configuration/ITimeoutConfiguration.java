package aquality.selenium.configuration;

import java.time.Duration;

/**
 * Describes timeouts configuration.
 */
public interface ITimeoutConfiguration extends aquality.selenium.core.configurations.ITimeoutConfiguration {

    /**
     * Gets WebDriver AsynchronousJavaScript timeout.
     *
     * @return AsynchronousJavaScript timeout.
     */
    Duration getScript();

    /**
     * Gets WebDriver PageLoad timeout.
     *
     * @return PageLoad timeout.
     */
    Duration getPageLoad();
}
