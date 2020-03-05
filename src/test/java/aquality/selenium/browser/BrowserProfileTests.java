package aquality.selenium.browser;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BrowserProfileTests {
    private static final String testedBooleanEnvironmentVariable = "isElementHighlightEnabled";
    @BeforeMethod
    public void setEnvironmentVar(){
        System.setProperty(testedBooleanEnvironmentVariable, "true");
    }

    @Test
    public void testBooleanEnvironmentVariableCouldBeSet() {
        Assert.assertTrue(AqualityServices.getBrowserProfile().isElementHighlightEnabled());
    }

    @Test
    public void testNotThrowsWhenFailedToGetBooleanValueFromString() {
        try {
            System.setProperty(testedBooleanEnvironmentVariable, "fake");
            AqualityServices.getBrowserProfile().isElementHighlightEnabled();
        }
        catch (Throwable e) {
            Assert.fail("Failure of parse to boolean should not throw", e);
        }

    }

    @AfterMethod
    public void resetEnvironmentVar(){
        System.clearProperty(testedBooleanEnvironmentVariable);
    }
}
