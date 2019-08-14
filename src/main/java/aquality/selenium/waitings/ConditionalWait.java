package aquality.selenium.waitings;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.Function;

public final class ConditionalWait {

    private static final Configuration configuration = Configuration.getInstance();

    private ConditionalWait() {
        throw new IllegalStateException("All methods are static in this 'ConditionalWait' class, class instance is not required");
    }

    /**
     * Waits for function will be true or return some except false.
     * Default timeout condition from settings is using.
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

    /**
     * Waits for some object from condition with default timeout from settings. Wait until it's not false or null.
     *
     * @param condition Condition for waiting {@link ExpectedCondition}
     * @param <T>       Object for waiting
     * @return Object from condition
     */
    public static <T> T waitFor(ExpectedCondition<T> condition) {
        return waitFor(condition, configuration.getTimeoutConfiguration().getCondition());
    }

    /**
     * Waits for some object from condition with timeout. Wait until it's not false or null.
     *
     * @param condition        Condition for waiting {@link ExpectedCondition}
     * @param timeOutInSeconds Timeout in seconds
     * @param <T>              Object for waiting
     * @return Object from condition
     */
    public static <T> T waitFor(ExpectedCondition<T> condition, long timeOutInSeconds) {
        return waitFor(condition, BrowserManager.getBrowser().getDriver(), timeOutInSeconds);
    }

    /**
     * Waits for function will be true or return some except false.
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
        getBrowser().setImplicitWaitTimeout(0L);
        Wait<F> wait = new FluentWait<>(waitWith)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(configuration.getTimeoutConfiguration().getPollingInterval()));
        try {
            return wait.until(condition);
        } finally {
            getBrowser().setImplicitWaitTimeout(configuration.getTimeoutConfiguration().getImplicit());
        }
    }

    private static Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}
