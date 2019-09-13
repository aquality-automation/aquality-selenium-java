package aquality.selenium.waitings;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import org.openqa.selenium.StaleElementReferenceException;
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

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();

        Timer timer = new Timer();
        boolean result = ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return false;
        }, "Condition should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), timeoutCondition, 7);

        assertFalse(result);
        assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsMetAndTimeoutIsOver(){
        Timer timer = new Timer();
        try {
            ConditionalWait.waitForTrue(() ->
            {
                timer.start();
                return false;
            }, waitForTimeoutCondition, waitForTimeoutPolling,"Condition should be true");
        } catch (TimeoutException e) {
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 7);
            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionIsMetAndDefaultTimeoutIsNotOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        Timer timer = new Timer();

        boolean result = ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), timeoutCondition, 0);

        assertTrue(result);
        assertTrue(durationSample.getDuration() < timeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndTimeoutIsNotOver() throws TimeoutException {
        Timer timer = new Timer();

        ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 0);
        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition(){
        ConditionalWait.waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        Timer timer = new Timer();

        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    },
                    "Condition should be true");

        }catch (org.openqa.selenium.TimeoutException e){
            DurationSample durationSample = new DurationSample(timer.duration(), timeoutCondition, 7);
            BrowserManager.getBrowser().quit();

            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOver(){
        Timer timer = new Timer();

        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(StaleElementReferenceException.class));

        }catch (org.openqa.selenium.TimeoutException e){
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 7);
            BrowserManager.getBrowser().quit();

            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndDefaultTimeoutIsNotOver(){
        Timer timer = new Timer();

        ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                },
                "Conditional should be true");
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 0);
        BrowserManager.getBrowser().quit();

        assertTrue(durationSample.getDuration() < getTimeoutConfig().getCondition());
    }

    @Test
    public void testExceptionShouldBeCatchedConditionIsMetAndDefaultTimeoutIsNotOver(){
        Timer timer = new Timer();

        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        throw new IllegalArgumentException("I am exception");
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        } catch (org.openqa.selenium.TimeoutException e){
            DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 7);
            BrowserManager.getBrowser().quit();

            assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver(){
        Timer timer = new Timer();

        ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        DurationSample durationSample = new DurationSample(timer.duration(), waitForTimeoutCondition, 0);
        BrowserManager.getBrowser().quit();

        assertTrue(durationSample.getDuration() < waitForTimeoutCondition);
    }

    private ITimeoutConfiguration getTimeoutConfig(){
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
