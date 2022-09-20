package tests.usecases.devtools;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.devtools.JavaScriptHandling;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getConditionalWait;

public class JavaScriptEventTests extends BaseTest {
    private static final Duration NEGATIVE_CONDITION_TIMEOUT = Duration.ofSeconds(5);
    private static JavaScriptHandling javaScriptEngine() {
        return AqualityServices.getBrowser().javaScriptEngine();
    }

    @Test
    public void testSubscribeToJavaScriptConsoleApiCalledEventAndUnsubscribeFromIt() {
        final String consoleApiScript = "console.log('Hello world!')";
        List<String> apiCalledMessages = new ArrayList<>();
        javaScriptEngine().addJavaScriptConsoleApiListener(consoleEvent -> apiCalledMessages.addAll(consoleEvent.getMessages()));
        getBrowser().executeScript(consoleApiScript);

        boolean hasCountIncreased = getConditionalWait().waitFor(() -> !apiCalledMessages.isEmpty());
        Assert.assertTrue(hasCountIncreased, "Some JS console API events should have been recorded, should be possible to subscribe to JS Console API called event");

        int previousCount = apiCalledMessages.size();

        javaScriptEngine().stopEventMonitoring();
        getBrowser().executeScript(consoleApiScript);
        getConditionalWait().waitFor(() -> apiCalledMessages.size() > previousCount, NEGATIVE_CONDITION_TIMEOUT);
        Assert.assertEquals(apiCalledMessages.size(), previousCount, "No more JS console API events should be recorded, should be possible to unsubscribe from JS Console API called event");
    }

    @Test
    public void testSubscribeToJavaScriptExceptionThrownEventAndUnsubscribeFromIt() {
        WelcomeForm welcomeForm = new WelcomeForm();
        List<String> errorMessages = new ArrayList<>();
        javaScriptEngine().addJavaScriptExceptionThrownListener(exception -> errorMessages.add(exception.getMessage()));

        getBrowser().goTo(welcomeForm.getUrl());
        welcomeForm.getSubTitleLabel().getJsActions().setAttribute("onclick", "throw new Error('Hello, world!')");
        welcomeForm.getSubTitleLabel().click();
        boolean isNotEmpty = getConditionalWait().waitFor(() -> !errorMessages.isEmpty());
        Assert.assertTrue(isNotEmpty, "Some JS exceptions events should have been recorded, should be possible to subscribe to JS Exceptions thrown event");

        int previousCount = errorMessages.size();
        javaScriptEngine().stopEventMonitoring();
        welcomeForm.getSubTitleLabel().click();
        getConditionalWait().waitFor(() -> errorMessages.size() > previousCount, NEGATIVE_CONDITION_TIMEOUT);
        Assert.assertEquals(previousCount, errorMessages.size(), "No more JS exceptions should be recorded, should be possible to unsubscribe from JS Exceptions thrown event");
    }
}
