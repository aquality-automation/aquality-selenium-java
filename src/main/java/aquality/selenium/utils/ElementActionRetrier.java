package aquality.selenium.utils;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.IRetryConfiguration;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.Optional;
import java.util.function.Supplier;

public class ElementActionRetrier {

    private ElementActionRetrier() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Retries the action when "StaleElementReferenceException" occurs.
     * @param runnable
     */
    public static void doWithRetry(Runnable runnable)
    {
        Supplier supplier = () -> {
            runnable.run();
            return true;
        };
        doWithRetry(supplier);
    }

    /**
     * Retries the function when "StaleElementReferenceException" occures.
     * @param function Supplier to be applied
     * @param <T> Return type of function
     * @return Result of the function
     */
    public static <T> T doWithRetry(Supplier<T> function)
    {
        IRetryConfiguration retryConfiguration = Configuration.getInstance().getRetryConfiguration();
        int retryAttemptsLeft = retryConfiguration.getNumber();
        long actualInterval = retryConfiguration.getPollingInterval();
        Optional<T> result = Optional.empty();
        while(retryAttemptsLeft >= 0)
        {
            try
            {
                result = Optional.of(function.get());
                break;
            }
            catch (Exception exception)
            {
                if (isExceptionHandled(exception) && retryAttemptsLeft != 0)
                {
                    try {
                        Thread.sleep(actualInterval);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    retryAttemptsLeft--;
                }
                else
                {
                    throw exception;
                }
            }
        }
        return result.orElse(null);
    }

    private static boolean isExceptionHandled(Exception exception)
    {
        return (exception instanceof StaleElementReferenceException) ||
                (exception instanceof ElementNotInteractableException);
    }
}