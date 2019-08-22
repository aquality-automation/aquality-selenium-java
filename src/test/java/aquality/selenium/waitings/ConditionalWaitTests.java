package aquality.selenium.waitings;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static utils.TimeUtil.getCurrentTimeInSeconds;
import static org.testng.Assert.*;

public class ConditionalWaitTests {

    private static final long waitForTimeoutCondition = 10;
    private static final long waitForTimeoutPolling = 150;

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();

        final double[] startTime = {0};
        boolean result = ConditionalWait.waitForTrue(() ->
        {
            if (startTime[0] == 0) {
                startTime[0] = getCurrentTimeInSeconds();
            }
            return false;
        }, "Condition should be true");
        double duration = getCurrentTimeInSeconds() - startTime[0];

        assertFalse(result);
        assertTrue(duration > timeoutCondition && duration < 2 * timeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsMetAndTimeoutIsOver(){
        final double[] startTime = {0};
        try {
            ConditionalWait.waitForTrue(() ->
            {
                if (startTime[0] == 0) {
                    startTime[0] = getCurrentTimeInSeconds();
                }
                return false;
            }, waitForTimeoutCondition, waitForTimeoutPolling,"Condition should be true");
        } catch (TimeoutException e) {
            double duration = getCurrentTimeInSeconds() - startTime[0];
            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionIsMetAndDefaultTimeoutIsNotOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        final double[] startTime = {0};
        boolean result = ConditionalWait.waitForTrue(() ->
        {
            if (startTime[0] == 0) {
                startTime[0] = getCurrentTimeInSeconds();
            }
            return true;
        }, "Timeout exception should not be thrown");
        double duration = getCurrentTimeInSeconds() - startTime[0];

        assertTrue(result);
        assertTrue(duration < timeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndTimeoutIsNotOver() throws TimeoutException {
        final double[] startTime = {0};
        ConditionalWait.waitForTrue(() ->
        {
            if (startTime[0] == 0) {
                startTime[0] = getCurrentTimeInSeconds();
            }
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        double duration = getCurrentTimeInSeconds() - startTime[0];

        assertTrue(duration < waitForTimeoutCondition);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition(){
        ConditionalWait.waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        final double[] startTime = {0};
        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        if (startTime[0] == 0) {
                            startTime[0] = getCurrentTimeInSeconds();
                        }
                        return false;
                    },
                    "Condition should be true");

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime[0];

            assertTrue(duration > timeoutCondition && duration < 2 * timeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOver(){
        final double[] startTime = {0};
        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        if (startTime[0] == 0) {
                            startTime[0] = getCurrentTimeInSeconds();
                        }
                        return false;
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(StaleElementReferenceException.class));

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime[0];

            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);

        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndDefaultTimeoutIsNotOver(){
        final double[] startTime = {0};
        ConditionalWait.waitFor((driver) ->
                {
                    if (startTime[0] == 0) {
                        startTime[0] = getCurrentTimeInSeconds();
                    }
                    return true;
                },
                "Conditional should be true");
        double duration = getCurrentTimeInSeconds() - startTime[0];

        assertTrue(duration < getTimeoutConfig().getCondition());
    }

    @Test
    public void testExceptionShouldBeCatchedConditionIsMetAndDefaultTimeoutIsNotOver(){
        final double[] startTime = {0};

        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        if (startTime[0] == 0) {
                            startTime[0] = getCurrentTimeInSeconds();
                        }
                        throw new IllegalArgumentException("I am exception");
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        } catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime[0];

            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver(){
        final double[] startTime = {0};

        ConditionalWait.waitFor((driver) ->
                {
                    if (startTime[0] == 0) {
                        startTime[0] = getCurrentTimeInSeconds();
                    }
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));

        double duration = getCurrentTimeInSeconds() - startTime[0];
        assertTrue(duration < waitForTimeoutCondition);
    }

    private ITimeoutConfiguration getTimeoutConfig(){
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
