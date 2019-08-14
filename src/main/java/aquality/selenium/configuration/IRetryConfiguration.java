package aquality.selenium.configuration;

public interface IRetryConfiguration {

    /**
     * Gets the number of attempts during retry.
     * @return Number of retry attempts
     */
    int getNumber();

    /**
     * Gets the polling interval used in retry.
     * @return Polling interval for retry
     */
    long getPollingInterval();
}
