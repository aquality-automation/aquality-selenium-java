package aquality.selenium.configuration;

/**
 * Describes timeouts configuration.
 */
public interface ITimeoutConfiguration {

    /**
     * Gets WedDriver ImplicitWait timeout.
     * @return ImplicitWait timeout.
     */
    long getImplicit();

    /**
     * Gets default ConditionalWait timeout.
     * @return ConditionalWait timeout.
     */
    long getCondition();

    /**
     * Gets WedDriver AsynchronousJavaScript timeout.
     * @return AsynchronousJavaScript timeout.
     */
    long getScript();

    /**
     * Gets WedDriver PageLoad timeout.
     * @return PageLoad timeout.
     */
    long getPageLoad();

    /**
     * Gets ConditionalWait polling interval.
     * @return ConditionalWait polling interval.
     */
    long getPollingInterval();

    /**
     * Gets Command timeout.
     * @return Command timeout.
     */
    long getCommand();
}
