package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.IActionRetrier;
import automationpractice.Constants;
import automationpractice.forms.ResourceLimitIsReachedForm;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;
import java.util.ArrayList;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static aquality.selenium.browser.AqualityServices.getConditionalWait;

public class AutomationPracticeUtils {

    public static void openAutomationPracticeSite() {
        ResourceLimitIsReachedForm resourceLimitIsReachedForm = new ResourceLimitIsReachedForm();
        getBrowser().goTo(Constants.URL_AUTOMATIONPRACTICE);
        getBrowser().waitForPageToLoad();
        getConditionalWait().waitFor(() -> {
                    if (resourceLimitIsReachedForm.state().isDisplayed()) {
                        getBrowser().refresh();
                        getBrowser().waitForPageToLoad();
                        return false;
                    }
                    return true;
                }, Duration.ofMinutes(3), Duration.ofSeconds(15),
                String.format("Failed to load [%1$s] website, [%2$s] is displayed", Constants.URL_AUTOMATIONPRACTICE, resourceLimitIsReachedForm.getName()));
    }

    public static void doOnAutomationPracticeWithRetry(Runnable testAction) {
        //website automationpractice.com is out of resources and unable to proceed operations sometimes
        Runnable action = () -> {
            restartAutomationPracticeSiteWhenResourceLimit();
            testAction.run();
        };
        AqualityServices.get(IActionRetrier.class).doWithRetry(action,
                new ArrayList<Class<? extends Throwable>>() {{
                    add(NoSuchElementException.class);
                    add(TimeoutException.class);
                    add(AssertionError.class);
                }});
    }

    private static void restartAutomationPracticeSiteWhenResourceLimit() {
        ResourceLimitIsReachedForm resourceLimitIsReachedForm = new ResourceLimitIsReachedForm();
        if (resourceLimitIsReachedForm.state().isDisplayed()) {
            getBrowser().quit();
            AutomationPracticeUtils.openAutomationPracticeSite();
            getBrowser().maximize();
        }
    }
}