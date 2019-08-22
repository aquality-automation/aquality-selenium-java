package aquality.selenium.waitings;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.localization.LocalizationManager;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

public final class ConditionalWait {

    private ConditionalWait() {
        throw new IllegalStateException("All methods are static in this 'ConditionalWait' class, class instance is not required");
    }

    /**
     * Wait for some condition within timeout. Method does not use WebDriverWait
     * Default values for timeouts used from configuration settings file
     * @param condition condition with boolean result (predicate)
     * @param message Part of error message in case of Timeout exception
     * @return true if the condition has been met during the timeout
     */
    public static boolean waitForTrue(BooleanSupplier condition, String message)
    {
        try
        {
            waitForTrue(condition, getTimeoutConfiguration().getCondition(), getTimeoutConfiguration().getPollingInterval(), message);
            return true;
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Wait for some condition within timeout. Method does not use WebDriverWait
     * @param condition condition with boolean result (predicate)
     * @param timeoutInSeconds Condition timeout
     * @param pollingIntervalInSeconds Condition check interval
     * @param message Part of error message in case of Timeout exception
     * @throws TimeoutException will be thrown in case if timeout is over but condition was not met
     * @throws InterruptedException will be thrown in case if thread is interrupted
     */
    public static void waitForTrue(BooleanSupplier condition, long timeoutInSeconds, long pollingIntervalInSeconds, String message) throws TimeoutException {
        if (condition == null)
        {
            throw new IllegalArgumentException(getLocalizationManager().getValue("loc.wait.condition.cant.be.null"));
        }

        double startTime = getCurrentTime();
        while (true)
        {
            if (condition.getAsBoolean())
            {
                return;
            }

            double currentTime = getCurrentTime();
            if ((currentTime - startTime) > timeoutInSeconds)
            {
                String exceptionMessage = String.format(getLocalizationManager().getValue("loc.wait.timeout.condition"), timeoutInSeconds, message);
                throw new TimeoutException(exceptionMessage);
            }

            try {
                Thread.sleep(pollingIntervalInSeconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Waits for function will be true or return some except false.
     * Default timeout condition from settings is using.
     *
     * @param condition Function for waiting {@link Function}
     * @param message the message that will be added to an error in case if the condition is not matched during the timeout
     * @param <T>       Type of object which is waiting
     * @return Object which waiting for or null - is exceptions occurred
     */
    public static <T> T waitFor(ExpectedCondition<T> condition, String message) {
        return waitFor(condition,
                getTimeoutConfiguration().getCondition(),
                getTimeoutConfiguration().getPollingInterval(),
                message,
                Collections.singleton(StaleElementReferenceException.class));
    }

    /**
     * Waits for function will be true or return some except false.
     *
     * @param condition        Function for waiting {@link Function}.,
     * @param timeOutInSeconds Time-out in seconds
     * @param pollingIntervalInMilliseconds interval in milliseconds between checks whether condition match
     * @param message the message that will be added to an error in case if the condition is not matched during the timeout
     * @param exceptionsToIgnore list of exceptions that should be ignored during waiting
     * @param <T>              Type of object which is waiting
     * @return Object which waiting for or null - is exceptions occured
     */
    public static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds, long pollingIntervalInMilliseconds, String message, Collection<Class<? extends Throwable>> exceptionsToIgnore) {
        getBrowser().setImplicitWaitTimeout(0L);
        WebDriverWait wait = new WebDriverWait(getBrowser().getDriver(), timeOutInSeconds);
        wait.pollingEvery(Duration.ofMillis(pollingIntervalInMilliseconds));
        wait.withMessage(message);
        wait.ignoreAll(exceptionsToIgnore);

        try {
            return wait.until(condition);
        } finally {
            getBrowser().setImplicitWaitTimeout(getTimeoutConfiguration().getImplicit());
        }
    }

    private static Browser getBrowser(){
        return BrowserManager.getBrowser();
    }

    private static ITimeoutConfiguration getTimeoutConfiguration(){
        return Configuration.getInstance().getTimeoutConfiguration();
    }

    private static LocalizationManager getLocalizationManager(){
        return LocalizationManager.getInstance();
    }

    private static double getCurrentTime(){
        return System.nanoTime()/Math.pow(10,9);
    }
}
