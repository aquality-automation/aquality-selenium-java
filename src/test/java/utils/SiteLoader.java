package utils;

import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.Constants;
import org.openqa.selenium.By;

import java.time.Duration;

import static aquality.selenium.browser.AqualityServices.*;

public class SiteLoader {

    public static void openAutomationPracticeSite() {
        ILabel resourceLimitLabel = getElementFactory()
                .getLabel(By.xpath("//h1[.='Resource Limit Is Reached']"), "Resource Limit Is Reached");
        getBrowser().goTo(Constants.URL_AUTOMATIONPRACTICE);
        getBrowser().waitForPageToLoad();
        getConditionalWait().waitFor(() -> {
                    if (resourceLimitLabel.state().isDisplayed()) {
                        getBrowser().refresh();
                        getBrowser().waitForPageToLoad();
                        return false;
                    }
                    return true;
                }, Duration.ofMinutes(2), Duration.ofSeconds(10),
                String.format("Failed to load [%1$s] website, [%2$s] is displayed", Constants.URL_AUTOMATIONPRACTICE, resourceLimitLabel.getName()));
    }
}