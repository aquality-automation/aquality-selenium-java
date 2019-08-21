package aquality.selenium.utils;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ElementActionRetrierTests {

    @DataProvider
    private Object[][] handledExceptions() {
        return new Object[][] {
                { new StaleElementReferenceException("") },
                { new InvalidElementStateException("")}
        };
    }

    @Test
    public void testRetrierShouldWorkOnceIfMethodSucceeded() {
        long operationTime = 500;
        Date startTime = new Date();
        ElementActionRetrier.doWithRetry(() -> Logger.getInstance().info("text message from successful method"));
        long duration = new Date().getTime() - startTime.getTime();
        assertTrue(duration < getPollingInterval() + operationTime);
    }

    @Test(dataProvider = "handledExceptions")
    public void testRetrierShouldWaitPollingTimeBetweenMethodsCall(RuntimeException handledException) {
        Date startTime = new Date();
        AtomicBoolean isThrowException = new AtomicBoolean(true);
        ElementActionRetrier.doWithRetry(() -> {
            if (isThrowException.get()) {
                isThrowException.set(false);
                throw handledException;
            }
        });
        long duration = new Date().getTime() - startTime.getTime();
        assertTrue(duration >= getPollingInterval() && duration < 2 * getPollingInterval());
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testRetrierShouldThrowUnhandledException() {
        ElementActionRetrier.doWithRetry(() -> {
            throw new InvalidArgumentException("");
        });
    }

    @Test(dataProvider = "handledExceptions")
    public void testRetrierShouldWorkCorrectTimes(RuntimeException handledException) {
        Date startTime = new Date();
        try {
            ElementActionRetrier.doWithRetry(() -> {
                throw handledException;
            });
        } catch (RuntimeException e) {
            assertTrue(handledException.getClass().isInstance(e));
        }
        long duration = new Date().getTime() - startTime.getTime();
        assertTrue(duration >= getPollingInterval() * getRetryNum() && duration < getPollingInterval() * (getRetryNum() + 1));
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ElementActionRetrier.doWithRetry(() -> {
                    isRetrierPaused.set(true);
                    throw handledException;
                });
            }
        });
        thread.start();
        while (!isRetrierPaused.get()) {
            Thread.sleep(getPollingInterval() / 10);
        }
        Thread.sleep(getPollingInterval() / 3);
        thread.interrupt();
    }

    private long getPollingInterval(){
        return Configuration.getInstance().getRetryConfiguration().getPollingInterval();
    }

    private int getRetryNum(){
        return Configuration.getInstance().getRetryConfiguration().getNumber();
    }
}