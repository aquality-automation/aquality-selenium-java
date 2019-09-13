package aquality.selenium.utils;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.DurationSample;
import utils.Timer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
        Timer timer = new Timer(Timer.TimeUnit.MILLISECONDS);
        timer.start();
        AtomicBoolean isThrowException = new AtomicBoolean(true);
        ElementActionRetrier.doWithRetry(() -> {
            if (isThrowException.get()) {
                isThrowException.set(false);
                throw handledException;
            }
        });
        DurationSample durationSample = new DurationSample( timer.duration(), pollingInterval, 200);

        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
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