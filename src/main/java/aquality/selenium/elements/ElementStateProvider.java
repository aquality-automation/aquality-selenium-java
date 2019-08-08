package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.IElementStateProvider;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

class ElementStateProvider implements IElementStateProvider {

    private static final long ZERO_TIMEOUT = 0L;
    private final By locator;

    ElementStateProvider(By locator) {
        this.locator = locator;
    }

    @Override
    public boolean isDisplayed() {
        return waitForDisplayed(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForDisplayed(long timeout) {
        return !findElements(timeout, ElementState.DISPLAYED).isEmpty();
    }

    @Override
    public boolean waitForNotDisplayed(long timeout) {
        return ConditionalWait.waitForTrue(driver -> !isDisplayed(), timeout);
    }

    @Override
    public boolean isExist() {
        return waitForExist(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForExist(long timeout) {
        return !findElements(timeout, ElementState.EXISTS_IN_ANY_STATE).isEmpty();
    }

    @Override
    public boolean waitForNotExist(long timeout) {
        return ConditionalWait.waitForTrue(driver -> !isExist(), timeout);
    }


    @Override
    public boolean isEnabled() {
        return waitForEnabled(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForEnabled(long timeout) {
        return ConditionalWait.waitForTrue(y -> {
            List<WebElement> webElements = findElements(timeout, ElementState.EXISTS_IN_ANY_STATE);
            if(!webElements.isEmpty()){
                WebElement webElement = webElements.get(0);
                return webElement.isEnabled() && !webElement.getAttribute(Attributes.CLASS.toString()).contains(PopularClassNames.DISABLED);
            }
            return false;
        }, timeout);
    }

    @Override
    public boolean waitForNotEnabled(long timeout) {
        return ConditionalWait.waitForTrue(driver -> !isEnabled(), timeout);
    }

    private List<WebElement> findElements(long timeout, ElementState state) {
        return ElementFinder.getInstance().findElements(getLocator(), timeout, state);
    }

    private By getLocator() {
        return locator;
    }
}
