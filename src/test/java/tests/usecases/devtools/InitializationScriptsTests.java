package tests.usecases.devtools;

import aquality.selenium.browser.AlertActions;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.InitializationScript;
import aquality.selenium.browser.devtools.JavaScriptHandling;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static aquality.selenium.browser.AqualityServices.getConditionalWait;

public class InitializationScriptsTests extends BaseTest {
    private static final String SCRIPT = "confirm('Hello world')";
    private static final String SCRIPT_NAME = "alert";
    private static JavaScriptHandling javaScriptEngine() {
        return AqualityServices.getBrowser().javaScriptEngine();
    }

    @Test
    public void testAddInitializationScriptGetItThenRemoveOrClear() {
        InitializationScript initScript = javaScriptEngine().addInitializationScript(SCRIPT_NAME, SCRIPT);
        Assert.assertNotNull(initScript, "Some initialization script model should be returned");
        Assert.assertEquals(SCRIPT, initScript.getScriptSource(), "Saved script source should match to expected");
        Assert.assertEquals(SCRIPT_NAME, initScript.getScriptName(), "Saved script name should match to expected");
        getBrowser().refresh();
        getBrowser().handleAlert(AlertActions.ACCEPT);
        getBrowser().refreshPageWithAlert(AlertActions.ACCEPT);

        Assert.assertTrue(javaScriptEngine().getInitializationScripts().contains(initScript),"Should be possible to read initialization scripts");

        javaScriptEngine().removeInitializationScript(initScript);
        getBrowser().refresh();
        Assert.assertThrows(NoAlertPresentException.class, () -> getBrowser().handleAlert(AlertActions.ACCEPT));
        Assert.assertTrue(javaScriptEngine().getInitializationScripts().isEmpty(), "Should be possible to read initialization scripts after remove");
        javaScriptEngine().addInitializationScript(SCRIPT_NAME, SCRIPT);
        getBrowser().refreshPageWithAlert(AlertActions.ACCEPT);
        Assert.assertEquals(javaScriptEngine().getInitializationScripts().size(), 1, "Exactly one script should be among initialization scripts");

        javaScriptEngine().clearInitializationScripts();
        Assert.assertThrows(NoAlertPresentException.class, () -> getBrowser().handleAlert(AlertActions.ACCEPT));
        Assert.assertTrue(javaScriptEngine().getInitializationScripts().isEmpty(), "Should be possible to read initialization scripts after remove");

        javaScriptEngine().addInitializationScript(SCRIPT_NAME, SCRIPT);
        getBrowser().refreshPageWithAlert(AlertActions.ACCEPT);
        javaScriptEngine().clearAll();
        Assert.assertThrows(NoAlertPresentException.class, () -> getBrowser().handleAlert(AlertActions.ACCEPT));
        Assert.assertTrue(javaScriptEngine().getInitializationScripts().isEmpty(), "Should be possible to read initialization scripts after clear all");
    }

    @Test(enabled = false)
    public void testAddScriptCallbackBindingSubscribeAndUnsubscribeGetItThenRemoveOrClear() throws TimeoutException {
        List<String> executedBindings = new ArrayList<>();
        javaScriptEngine().startEventMonitoring();
        javaScriptEngine().addInitializationScript(SCRIPT_NAME, SCRIPT);
        javaScriptEngine().addBindingCalledListener(executedBindings::add);
        getBrowser().refreshPageWithAlert(AlertActions.ACCEPT);

        javaScriptEngine().addScriptCallbackBinding(SCRIPT_NAME);
        Assert.assertThrows(NoAlertPresentException.class, () -> getBrowser().handleAlert(AlertActions.ACCEPT));
        getConditionalWait().waitForTrue(() -> executedBindings.contains(SCRIPT_NAME), "Subscription to JavaScriptCallbackExecuted event should work");
        int oldCount = executedBindings.size();
        getBrowser().refresh();
        Assert.assertTrue(executedBindings.size() > oldCount, "Another event should be noticed");
        Assert.assertTrue(javaScriptEngine().getScriptCallbackBindings().contains(SCRIPT_NAME), "Should be possible to read script callback bindings");
        oldCount = executedBindings.size();

        javaScriptEngine().removeScriptCallbackBinding(SCRIPT_NAME);
        Assert.assertTrue(javaScriptEngine().getScriptCallbackBindings().isEmpty(), "Should be possible to read script callback bindings after remove");
        javaScriptEngine().addScriptCallbackBinding(SCRIPT_NAME);
        Assert.assertTrue(javaScriptEngine().getScriptCallbackBindings().contains(SCRIPT_NAME), "Should be possible to read script callback bindings");
        javaScriptEngine().clearScriptCallbackBindings();
        Assert.assertTrue(javaScriptEngine().getScriptCallbackBindings().isEmpty(), "Should be possible to read script callback bindings after remove");

        javaScriptEngine().reset();
        getBrowser().refresh();
        Assert.assertEquals(executedBindings.size(), oldCount, "Another event should not be noticed, should be possible to unsubscribe from JavaScriptCallbackExecuted event");
    }
}
