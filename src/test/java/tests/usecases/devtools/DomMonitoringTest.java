package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.JavaScriptHandling;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static aquality.selenium.browser.AqualityServices.getConditionalWait;

public class DomMonitoringTest extends BaseTest {
    private static final Duration NEGATIVE_CONDITION_TIMEOUT = Duration.ofSeconds(5);

    private static JavaScriptHandling javaScriptEngine() {
        return AqualityServices.getBrowser().javaScriptEngine();
    }

    @Test
    public void testSubscribeToDomMutationEventAndDisableMonitoring() throws TimeoutException {
        final String attributeName = "cheese";
        final String attributeValue = "Gouda";
        WelcomeForm welcomeForm = new WelcomeForm();

        List<DomMutationEvent> attributeValueChanges = new ArrayList<>();
        javaScriptEngine().addDomMutatedListener(attributeValueChanges::add);
        getBrowser().goTo(welcomeForm.getUrl());

        welcomeForm.getSubTitleLabel().getJsActions().setAttribute(attributeName, attributeValue);
        getConditionalWait().waitForTrue(() -> !attributeValueChanges.isEmpty(),
                "Some mutation events should be found, should be possible to subscribe to DOM mutation event");
        Assert.assertEquals(attributeValueChanges.size(), 1, "Exactly one change in DOM is expected");
        DomMutationEvent record = attributeValueChanges.get(0);
        Assert.assertEquals(record.getAttributeName(), attributeName, "Attribute name should match to expected");
        Assert.assertEquals(record.getCurrentValue(), attributeValue, "Attribute value should match to expected");

        javaScriptEngine().reset();
        welcomeForm.getSubTitleLabel().getJsActions().setAttribute(attributeName, attributeName);
        getConditionalWait().waitFor(() -> !attributeValueChanges.isEmpty(), NEGATIVE_CONDITION_TIMEOUT);
        Assert.assertEquals(attributeValueChanges.size(), 1, "No more changes in DOM is expected, should be possible to unsubscribe from DOM mutation event");
    }
}
