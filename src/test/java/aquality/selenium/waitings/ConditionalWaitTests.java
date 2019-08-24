package aquality.selenium.waitings;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Timer;

import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ConditionalWaitTests {

    private static final long waitForTimeoutCondition = 10;
    private static final long waitForTimeoutPolling = 150;
    private Timer timer;

    @BeforeMethod
    public void initTimer(){
        timer = new Timer();
    }

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();

        boolean result = ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return false;
        }, "Condition should be true");
        double duration = timer.stop();

        assertFalse(result);
        assertTrue(duration > timeoutCondition && duration < 2 * timeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsMetAndTimeoutIsOver(){
        try {
            ConditionalWait.waitForTrue(() ->
            {
                timer.start();
                return false;
            }, waitForTimeoutCondition, waitForTimeoutPolling,"Condition should be true");
        } catch (TimeoutException e) {
            double duration = timer.stop();
            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionIsMetAndDefaultTimeoutIsNotOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();

        boolean result = ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, "Timeout exception should not be thrown");
        double duration = timer.stop();

        assertTrue(result);
        assertTrue(duration < timeoutCondition);
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndTimeoutIsNotOver() throws TimeoutException {
        ConditionalWait.waitForTrue(() ->
        {
            timer.start();
            return true;
        }, waitForTimeoutCondition, waitForTimeoutPolling, "Timeout exception should not be thrown");
        double duration = timer.stop();

        assertTrue(duration < waitForTimeoutCondition);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition(){
        ConditionalWait.waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    },
                    "Condition should be true");

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = timer.stop();

            assertTrue(duration > timeoutCondition && duration < 2 * timeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOver(){
        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        return false;
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(StaleElementReferenceException.class));

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = timer.stop();

            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);

        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndDefaultTimeoutIsNotOver(){
        ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                },
                "Conditional should be true");
        double duration = timer.stop();

        assertTrue(duration < getTimeoutConfig().getCondition());
    }

    @Test
    public void testExceptionShouldBeCatchedConditionIsMetAndDefaultTimeoutIsNotOver(){
        try{
            ConditionalWait.waitFor((driver) ->
                    {
                        timer.start();
                        throw new IllegalArgumentException("I am exception");
                    }, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        } catch (org.openqa.selenium.TimeoutException e){
            double duration = timer.stop();

            assertTrue(duration > waitForTimeoutCondition && duration < 2 * waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver(){
        ConditionalWait.waitFor((driver) ->
                {
                    timer.start();
                    return true;
                }, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        double duration = timer.stop();

        assertTrue(duration < waitForTimeoutCondition);
    }

    private ITimeoutConfiguration getTimeoutConfig(){
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
