package aquality.selenium.waitings;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.logger.Logger;
import com.google.common.base.Function;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public final class ConditionalWait {

    private static final Logger logger = Logger.getInstance();
    private static final Configuration configuration = Configuration.getInstance();

    private ConditionalWait() {
        throw new IllegalStateException("All methods are static in this 'ConditionalWait' class, class instance is not required");
    }

    /**
     * Wait for some object from condition with default timeout (property "defaultConditionTimeout" from
     * selenium.properties). Wait until it's not false or null.
     *
     * @param condition Condition for waiting {@link ExpectedCondition}
     * @param <T>       Object for waiting
     * @return Object from condition
     */
    public static <T> T waitFor(ExpectedCondition<T> condition) {
        return waitFor(condition, configuration.getTimeoutConfiguration().getCondition());
    }

    /**
     * Wait for some object from condition with timeout. Wait until it's not false or null.
     *
     * @param condition        Condition for waiting {@link ExpectedCondition}
     * @param timeOutInSeconds Timeout in seconds
     * @param <T>              Object for waiting
     * @return Object from condition
     */
    public static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds) {
        getBrowser().setImplicitlyWaitTimeout(0L);
        Wait<WebDriver> wait = new FluentWait<>((WebDriver) getBrowser().getDriver())
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(configuration.getTimeoutConfiguration().getPollingInterval()))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);

        try {
            return wait.until(condition);
        } catch (Exception | AssertionError e) {
            logger.debug("java.ConditionalWait.waitFor", e);
        } finally {
            getBrowser().setImplicitlyWaitTimeout(configuration.getTimeoutConfiguration().getImplicit());
        }
        return null;
    }

    /**
     * Wait for condition and return true if waiting successful or false - otherwise.
     * Default timeout (property "defaultConditionTimeout" from selenium.properties) is using.
     *
     * @param condition Condition for waiting {@link ExpectedCondition}
     * @return True if waiting successful or false - otherwise.
     */
    public static boolean waitForTrue(ExpectedCondition<Boolean> condition) {
        try {
            return waitFor(condition);
        } catch (Exception e) {
            logger.debug("java.ConditionalWait.waitForTrue", e);
            return false;
        }
    }


    /**
     * Wait for condition and return true if waiting successful or false - otherwise.
     *
     * @param condition        Condition for waiting {@link ExpectedCondition}
     * @param timeOutInSeconds Time out in seconds for waiting
     * @return True if waiting successful or false - otherwise.
     */
    public static boolean waitForTrue(ExpectedCondition<Boolean> condition, long timeOutInSeconds) {
        try {
            return waitFor(condition, timeOutInSeconds);
        } catch (Exception e) {
            logger.debug("java.ConditionalWait.waitForTrue", e);
            return false;
        }
    }

    /**
     * For waiting without WebDriver: Wait for function will be true or return some except false.
     *
     * @param condition        Function for waiting {@link Function}.,
     *
     * @param waitWith         Object who will helping to wait (which will be passed to {@link Function#apply(Object)})
     * @param timeOutInSeconds Time-out in seconds
     * @param <F>              Type of waitWith param
     * @param <T>              Type of object which is waiting
     * @return Object which waiting for or null - is exceptions occured
     */
    public static <F, T> T waitFor(Function<F, T> condition, F waitWith, long timeOutInSeconds) {
        Wait<F> wait = new FluentWait<>(waitWith)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(configuration.getTimeoutConfiguration().getPollingInterval()));
        try {
            return wait.until(condition);
        } catch (Exception | AssertionError e) {
            logger.debug("java.ConditionalWait.waitFor", e);
        }
        return null;
    }

    /**
     * For waiting without WebDriver: Wait for function will be true or return some except false.
     * Default timeout (property "defaultConditionTimeout" from selenium.properties) is using.
     *
     * @param condition Function for waiting {@link Function}
     * @param waitWith  Object who will helping to wait (which will be passed to {@link Function#apply(Object)})
     * @param <F>       Type of waitWith param
     * @param <T>       Type of object which is waiting
     * @return Object which waiting for or null - is exceptions occured
     */
    public static <F, T> T waitFor(Function<F, T> condition, F waitWith) {
        return waitFor(condition, waitWith, configuration.getTimeoutConfiguration().getCondition());
    }

    private static Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}
