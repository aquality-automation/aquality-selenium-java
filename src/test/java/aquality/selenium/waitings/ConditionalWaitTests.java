package aquality.selenium.waitings;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.core.waitings.IConditionalWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.DurationSample;
import utils.Timer;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeoutException;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ConditionalWaitTests {

    private static final Duration waitForTimeoutCondition = Duration.ofSeconds(10);
    private static final Duration waitForTimeoutPolling = Duration.ofMillis(150);
    private static final double defaultDeviation = 7;

    private IConditionalWait getConditionalWait(){
        return AqualityServices.getConditionalWait();
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        Assert.expectThrows(TimeoutException.class, () -> getConditionalWait().waitForTrue(() ->
        {
            timer.start();
            return false;
        }, "Condition should be true"));
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition(), defaultDeviation);
        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsNotMetAndTimeoutIsOver() {
        Timer timer = new Timer();
        Assert.expectThrows(TimeoutException.class, () -> getConditionalWait().waitForTrue(() ->
        {
            timer.start();
            return false;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Condition should be true"));
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndTimeoutIsNotOver() throws TimeoutException {
        Timer timer = new Timer();
        getConditionalWait().waitForTrue(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition.getSeconds());
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndDefaultTimeoutIsNotOver() throws TimeoutException {
        Timer timer = new Timer();
        getConditionalWait().waitForTrue(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition().getSeconds());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition() throws TimeoutException {
        getConditionalWait().waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    },
                    "Condition should be true");

        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition(), defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOver() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true");

        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOverWithIgnoredExceptions() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.emptyList());

        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndDefaultTimeoutIsNotOver() {
        Timer timer = new Timer();

        getConditionalWait().waitFor((driver) ->
                {
                    timer.start();
                    return true;
                },
                "Conditional should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition().getSeconds());
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOverWithIgnoredExceptions() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition.getSeconds());
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition.getSeconds());
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testExceptionShouldBeCaughtConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
                    {
                        timer.start();
                        throw new IllegalArgumentException("I am exception");
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testStaleElementReferenceExceptionShouldBeCaughtConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
                    {
                        timer.start();
                        throw new StaleElementReferenceException("I am StaleElementReferenceException");
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true");
        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testStaleElementReferenceExceptionShouldBeCaughtConditionIsMetAndDefaultTimeoutIsNotOver() {
        Timer timer = new Timer();
        try {
            getConditionalWait().waitFor((driver) ->
            {
                timer.start();
                throw new StaleElementReferenceException("I am StaleElementReferenceException");
            }, "Conditional should be true");
        } catch (org.openqa.selenium.TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition(), defaultDeviation);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTrueShouldBeReturnedIfConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition.getSeconds());
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndTimeoutIsOver() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor(() ->
        {
            timer.start();
            return false;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, defaultDeviation);
        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        assertFalse(conditionResult, "Condition result should be false");
    }

    @Test
    public void testTrueShouldBeReturnedIfConditionIsMetAndDefaultTimeoutIsNotOver() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition().getSeconds());
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        boolean conditionResult = getConditionalWait().waitFor(() ->
        {
            timer.start();
            return false;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition(), defaultDeviation);
        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        assertFalse(conditionResult, "Condition result should be false");
    }

    @AfterMethod
    public void after() {
        AqualityServices.getBrowser().quit();
    }

    private ITimeoutConfiguration getTimeoutConfig() {
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
