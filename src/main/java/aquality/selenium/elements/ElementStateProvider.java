package aquality.selenium.elements;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.DefaultElementStateProvider;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.waitings.IConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class ElementStateProvider extends DefaultElementStateProvider {

    private static final String WAIT_FOR_STATE_KEY = "loc.waitinstate";
    private final By locator;

    public ElementStateProvider(By locator, IConditionalWait conditionalWait, IElementFinder elementFinder) {
        super(locator, conditionalWait, elementFinder);
        this.locator = locator;
    }

    private void logLocInfo(String key, Object... args) {
        AqualityServices.getLocalizedLogger().info(key, args);
    }

    @Override
    protected boolean isElementEnabled(WebElement element) {
        return element.isEnabled() && !element.getAttribute(Attributes.CLASS.toString()).contains("disabled");
    }

    @Override
    public void waitForClickable(Duration timeout) {
        logLocInfo(WAIT_FOR_STATE_KEY, elementClickable().getStateName(), locator);
        super.waitForClickable(timeout);
    }

    @Override
    public boolean waitForDisplayed(Duration timeout) {
        logLocInfo(WAIT_FOR_STATE_KEY, ElementState.DISPLAYED, locator);
        return super.waitForDisplayed(timeout);
    }

    @Override
    public boolean waitForNotDisplayed(Duration timeout) {
        logLocInfo("loc.waitinvisible", locator);
        return super.waitForNotDisplayed(timeout);
    }

    @Override
    public boolean waitForExist(Duration timeout) {
        logLocInfo("loc.waitexists", locator);
        return super.waitForExist(timeout);
    }

    @Override
    public boolean waitForNotExist(Duration timeout) {
        logLocInfo("loc.waitnotexists", locator);
        return super.waitForNotExist(timeout);
    }

    @Override
    public boolean waitForEnabled(Duration timeout) {
        logLocInfo(WAIT_FOR_STATE_KEY, elementEnabled().getStateName(), locator);
        return super.waitForEnabled(timeout);
    }

    @Override
    public boolean waitForNotEnabled(Duration timeout) {
        logLocInfo(WAIT_FOR_STATE_KEY, elementNotEnabled().getStateName(), locator);
        return super.waitForNotEnabled(timeout);
    }
}
