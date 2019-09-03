package aquality.selenium.utils;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.logger.Logger;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Timer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ElementActionRetrierTests {

    private static final int retriesCount = Configuration.getInstance().getRetryConfiguration().getNumber();
    private static final long pollingInterval = Configuration.getInstance().getRetryConfiguration().getPollingInterval();


    @DataProvider
    private Object[][] handledExceptions() {
        return new Object[][] {
                { new StaleElementReferenceException("") },
                { new InvalidElementStateException("")}
        };
    }

    @Test
    public void testRetrierShouldWorkOnceIfMethodSucceeded() {
        Date startTime = new Date();
        ElementActionRetrier.doWithRetry(() -> true);
        assertTrue(new Date().getTime() - startTime.getTime() < pollingInterval);
    }

    @Test(dataProvider = "handledExceptions")
    public void testRetrierShouldWaitPollingTimeBetweenMethodsCall(RuntimeException handledException) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AtomicBoolean isThrowException = new AtomicBoolean(true);
        ElementActionRetrier.doWithRetry(() -> {
            if (isThrowException.get()) {
                isThrowException.set(false);
                throw handledException;
            }
        });
        stopWatch.stop();

        long duration = stopWatch.getTime(TimeUnit.MILLISECONDS);
        assertTrue(duration >= pollingInterval, "duration should be more than polling interval. actual is " + duration + " milliseconds");
        assertTrue(duration <= 2 * pollingInterval, "duration should be less than doubled polling interval. actual is " + duration + " milliseconds");
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testRetrierShouldThrowUnhandledException() {
        ElementActionRetrier.doWithRetry(() -> {
            throw new InvalidArgumentException("");
        });
    }

    @Test(dataProvider = "handledExceptions")
    public void testRetrierShouldWorkCorrectTimes(RuntimeException handledException) {
        AtomicInteger actualAttempts = new AtomicInteger(0);
        try {
            ElementActionRetrier.doWithRetry(() -> {
                Logger.getInstance().info("current attempt is " + actualAttempts.incrementAndGet());
                throw handledException;
            });
        } catch (RuntimeException e) {
            assertTrue(handledException.getClass().isInstance(e));
        }
        assertEquals(actualAttempts.get(), retriesCount + 1, "actual attempts count is not match to expected");
    }

    @Test(expectedExceptions = IllegalAccessException.class)
    public void testShouldNotBePossibleInstantiateRetrier() throws IllegalAccessException, InstantiationException {
        ElementActionRetrier.class.newInstance();
    }

    @Test(expectedExceptions = InvocationTargetException.class)
    public void testShouldNotBePossibleInstantiateRetrierEvenIfUsingReflection() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<ElementActionRetrier> constructor = ElementActionRetrier.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testRetrierShouldReturnValue() {
        Object obj = new Object();
        assertEquals(ElementActionRetrier.doWithRetry(() -> obj), obj);
    }

    @Test(dataProvider = "handledExceptions", timeOut = 10000)
    public void testRetrierShouldNotThrowExceptionOnInterruption(RuntimeException handledException) throws InterruptedException {
        AtomicBoolean isRetrierPaused = new AtomicBoolean(false);
        Thread thread = new Thread(() -> ElementActionRetrier.doWithRetry(() -> {
            isRetrierPaused.set(true);
            throw handledException;
        }));
        thread.start();
        while (!isRetrierPaused.get()) {
            Thread.sleep(pollingInterval / 10);
        }
        Thread.sleep(pollingInterval / 3);
        thread.interrupt();
    }

}