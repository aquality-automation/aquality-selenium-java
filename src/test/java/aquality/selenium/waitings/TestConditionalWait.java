package aquality.selenium.waitings;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static utils.TimeUtil.getCurrentTimeInSeconds;

public class TestConditionalWait {

    private final long waitForTrueTimeoutCondition = 5;
    private final long waitForTrueTimeoutPolling = 300;
    private final long waitForTrueTimeoutOperation = 1;

    private final long waitForTimeoutCondition = 15;
    private final long waitForTimeoutPolling = 300;
    private final long waitForTimeoutOperation = 15; // big enough,  because if we run with grid it can take some time to establish connection

    @Test
    public void testFalseShouldBeReturnedIfConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();

        double startTime = getCurrentTimeInSeconds();
        boolean result = ConditionalWait.waitForTrue(() -> false, "Condition should be false");
        double duration = getCurrentTimeInSeconds() - startTime;

        Assert.assertFalse(result);
        Assert.assertTrue(duration < timeoutCondition + waitForTrueTimeoutOperation);
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfConditionIsMetAndTimeoutIsOver(){
        double startTime = getCurrentTimeInSeconds();
        try {
            ConditionalWait.waitForTrue(() -> false, waitForTrueTimeoutCondition, waitForTrueTimeoutPolling,"Condition should be false");
        } catch (TimeoutException e) {
            double duration = getCurrentTimeInSeconds() - startTime;
            Assert.assertTrue(duration < waitForTrueTimeoutCondition + waitForTrueTimeoutOperation);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionIsMetAndDefaultTimeoutIsNotOver(){
        double startTime = getCurrentTimeInSeconds();
        boolean result = ConditionalWait.waitForTrue(() -> true, "Timeout exception should not be thrown");
        double duration = getCurrentTimeInSeconds() - startTime;

        Assert.assertTrue(result);
        Assert.assertTrue(duration < waitForTrueTimeoutOperation*2);
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfConditionMetAndTimeoutIsNotOver() throws TimeoutException {
        double startTime = getCurrentTimeInSeconds();
        ConditionalWait.waitForTrue(() -> true, waitForTrueTimeoutCondition, waitForTrueTimeoutPolling, "Timeout exception should not be thrown");
        double duration = getCurrentTimeInSeconds() - startTime;

        Assert.assertTrue(duration < waitForTrueTimeoutOperation*2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCannotBePassedAsCondition(){
        ConditionalWait.waitForTrue(null, "Condition should not be null");
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndDefaultTimeoutIsOver(){
        long timeoutCondition = getTimeoutConfig().getCondition();
        double startTime = getCurrentTimeInSeconds();
        try{
            ConditionalWait.waitFor((driver) -> false,
                    "Conditional should be true");

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime;

            Assert.assertTrue(duration < timeoutCondition + waitForTimeoutOperation);
            Assert.assertTrue(duration > timeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldBeThrownIfDriverConditionIsNotMetAndTimeoutIsOver(){
        double startTime = getCurrentTimeInSeconds();
        try{
            ConditionalWait.waitFor((driver) -> false, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(StaleElementReferenceException.class));

        }catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime;

            Assert.assertTrue(duration < waitForTimeoutCondition + waitForTimeoutOperation);
            Assert.assertTrue(duration > waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndDefaultTimeoutIsNotOver(){
        long timeoutOperation = 15;

        double startTime = getCurrentTimeInSeconds();
        ConditionalWait.waitFor((driver) -> true,
                "Conditional should be true");
        double duration = getCurrentTimeInSeconds() - startTime;

        Assert.assertTrue(duration <= timeoutOperation);
    }

    @Test
    public void testExceptionShouldBeCatchedConditionIsMetAndDefaultTimeoutIsNotOver(){
        double startTime = getCurrentTimeInSeconds();

        try{
            ConditionalWait.waitFor((driver) -> {throw new IllegalArgumentException("I am exception");}, waitForTimeoutCondition, waitForTimeoutPolling,
                    "Conditional should be true", Collections.singleton(IllegalArgumentException.class));
        }catch (org.openqa.selenium.TimeoutException e){
            double duration = getCurrentTimeInSeconds() - startTime;

            Assert.assertTrue(duration < waitForTimeoutCondition + waitForTimeoutOperation);
            Assert.assertTrue(duration > waitForTimeoutCondition);
        }
    }

    @Test
    public void testTimeoutExceptionShouldNotBeThrownIfDriverConditionIsMetAndTimeoutIsNotOver(){
        double startTime = getCurrentTimeInSeconds();

        ConditionalWait.waitFor((driver) -> true, waitForTimeoutCondition, waitForTimeoutPolling,
                "Conditional should be true", Collections.singleton(IllegalArgumentException.class));

        double duration = getCurrentTimeInSeconds() - startTime;

        Assert.assertTrue(duration <= waitForTimeoutOperation);
    }

    private ITimeoutConfiguration getTimeoutConfig(){
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
