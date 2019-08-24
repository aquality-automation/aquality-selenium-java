package aquality.selenium.utils;

import aquality.selenium.configuration.Configuration;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.*;

public class ElementActionRetrierTests {

    private static final int attemptsCount = Configuration.getInstance().getRetryConfiguration().getNumber();
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
        Date startTime = new Date();
        AtomicBoolean isThrowException = new AtomicBoolean(true);
        ElementActionRetrier.doWithRetry(() -> {
            if (isThrowException.get()) {
                isThrowException.set(false);
                throw handledException;
            }
        });
        long duration = new Date().getTime() - startTime.getTime();
        assertTrue(duration >= pollingInterval && duration < 2 * pollingInterval);
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
        assertTrue(duration >= pollingInterval * attemptsCount && duration < pollingInterval * (attemptsCount + 1));
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