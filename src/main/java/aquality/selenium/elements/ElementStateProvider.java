package aquality.selenium.elements;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.interfaces.IElementStateProvider;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
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
        getLogger().info(getLocManager().getValue("loc.waitinstate"), ElementState.DISPLAYED, getLocator());
        return !findElements(timeout, ElementState.DISPLAYED).isEmpty();
    }

    @Override
    public boolean waitForDisplayed() {
        return waitForDisplayed(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotDisplayed(long timeout) {
        getLogger().info(getLocManager().getValue("loc.waitinvisible"));
        return ConditionalWait.waitForTrue(driver -> !isDisplayed(), timeout);
    }

    @Override
    public boolean waitForNotDisplayed() {
        return waitForNotDisplayed(getDefaultTimeout());
    }

    @Override
    public boolean isExist() {
        return waitForExist(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForExist(long timeout) {
        getLogger().info(getLocManager().getValue("loc.waitexists"));
        return !findElements(timeout, ElementState.EXISTS_IN_ANY_STATE).isEmpty();
    }

    @Override
    public boolean waitForExist() {
       return waitForExist(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotExist(long timeout) {
        getLogger().info(getLocManager().getValue("loc.waitnotexists"), timeout);
        return ConditionalWait.waitForTrue(driver -> !isExist(), timeout);
    }

    @Override
    public boolean waitForNotExist() {
        return waitForNotExist(getDefaultTimeout());
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
    public boolean waitForEnabled() {
        return waitForEnabled(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotEnabled(long timeout) {
        return ConditionalWait.waitForTrue(driver -> !isEnabled(), timeout);
    }

    @Override
    public boolean waitForNotEnabled() {
        return waitForNotEnabled(getDefaultTimeout());
    }

    private List<WebElement> findElements(long timeout, ElementState state) {
        return ElementFinder.getInstance().findElements(getLocator(), timeout, state);
    }

    private By getLocator() {
        return locator;
    }

    private Logger getLogger(){
        return Logger.getInstance();
    }

    private LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }

    private long getDefaultTimeout(){
        return Configuration.getInstance().getTimeoutConfiguration().getCondition();
    }
}
