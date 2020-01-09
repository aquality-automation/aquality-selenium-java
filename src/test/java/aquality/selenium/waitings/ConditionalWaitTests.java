package aquality.selenium.waitings;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.DurationSample;
import utils.Timer;
import java.util.Collections;
import java.util.concurrent.TimeoutException;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ConditionalWaitTests {

    private static final long waitForTimeoutCondition = 10;
    private static final long waitForTimeoutPolling = 150;
    private static final double defaultDeviation = 7;

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        Assert.expectThrows(TimeoutException.class, () -> ConditionalWait.waitForTrue(() ->
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
        Assert.expectThrows(TimeoutException.class, () -> ConditionalWait.waitForTrue(() ->
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
        ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndDefaultTimeoutIsNotOver() throws TimeoutException {
        Timer timer = new Timer();
        ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition() throws TimeoutException {
        ConditionalWait.waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        try {
            ConditionalWait.waitFor((driver) ->
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
            ConditionalWait.waitFor((driver) ->
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
            ConditionalWait.waitFor((driver) ->
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

        ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                },
                "Conditional should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition());
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOverWithIgnoredExceptions() {
        Timer timer = new Timer();
        boolean conditionResult = ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        boolean conditionResult = ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testExceptionShouldBeCaughtConditionIsMetAndTimeoutIsNotOver() {
        Timer timer = new Timer();
        try {
            ConditionalWait.waitFor((driver) ->
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
            ConditionalWait.waitFor((driver) ->
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
            ConditionalWait.waitFor((driver) ->
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
        boolean conditionResult = ConditionalWait.waitFor(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndTimeoutIsOver() {
        Timer timer = new Timer();
        boolean conditionResult = ConditionalWait.waitFor(() ->
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
        boolean conditionResult = ConditionalWait.waitFor(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), getTimeoutConfig().getCondition());
        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition());
        assertTrue(conditionResult, "Condition result should be true");
    }

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver() {
        Timer timer = new Timer();
        boolean conditionResult = ConditionalWait.waitFor(() ->
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
        BrowserManager.getBrowser().quit();
    }

    private ITimeoutConfiguration getTimeoutConfig() {
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
