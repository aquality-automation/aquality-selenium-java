package aquality.selenium.elements;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.interfaces.IElementStateProvider;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

class ElementStateProvider implements IElementStateProvider {

    private static final long ZERO_TIMEOUT = 0L;
    private final By locator;

    ElementStateProvider(By locator) {
        this.locator = locator;
    }

    @Override
    public boolean isClickable() {
        return waitForClickable(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForClickable(long timeout) {
        String stateName = "CLICKABLE";
        getLogger().info(String.format(getLocManager().getValue("loc.waitinstate"), stateName, getLocator()));
        DesiredState desiredState = new DesiredState(element -> element.isDisplayed() && element.isEnabled(), getDesiredStateMessage(stateName, timeout), false, true);
        return isElementInDesiredCondition(timeout, desiredState);
    }

    @Override
    public boolean waitForClickable() {
        return waitForClickable(getDefaultTimeout());
    }

    @Override
    public boolean isDisplayed() {
        return waitForDisplayed(ZERO_TIMEOUT);
    }

    @Override
    public boolean waitForDisplayed(long timeout) {
        getLogger().info(String.format(getLocManager().getValue("loc.waitinstate"), ElementState.DISPLAYED, getLocator()));
        DesiredState desiredState = new DesiredState(WebElement::isDisplayed, getDesiredStateMessage("DISPLAYED", timeout), true, false);
        return isElementInDesiredCondition(timeout, desiredState);
    }

    @Override
    public boolean waitForDisplayed() {
        return waitForDisplayed(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotDisplayed(long timeout) {
        getLogger().info(getLocManager().getValue("loc.waitinvisible"));
        DesiredState desiredState = new DesiredState(element -> !element.isDisplayed(), getDesiredStateMessage("NOT DISPLAYED", timeout), true, false);
        return isElementInDesiredCondition(timeout, desiredState);
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
        DesiredState desiredState = new DesiredState(Objects::nonNull, getDesiredStateMessage("EXIST", timeout), true, false);
        return isElementInDesiredCondition(timeout, desiredState);
    }

    @Override
    public boolean waitForExist() {
       return waitForExist(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotExist(long timeout) {
        getLogger().info(getLocManager().getValue("loc.waitnotexists"), timeout);
        try{
            long zeroTimeout = 0L;
            return ConditionalWait.waitForTrue(y -> findElements(zeroTimeout).isEmpty(), timeout);
        }catch (TimeoutException e){
            getLogger().debug(getDesiredStateMessage("NOT EXIST", timeout));
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
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
        DesiredState desiredState = new DesiredState(y ->
                findElements(timeout).
                        stream().anyMatch(element ->
                        element.isEnabled() &&
                                !element.getAttribute(Attributes.CLASS.toString()).contains(PopularClassNames.DISABLED)
                ), getDesiredStateMessage("ENABLED", timeout));
        return isElementInDesiredCondition(timeout, desiredState);
    }

    @Override
    public boolean waitForEnabled() {
        return waitForEnabled(getDefaultTimeout());
    }

    @Override
    public boolean waitForNotEnabled(long timeout) {
        DesiredState desiredState = new DesiredState(driver -> !isEnabled(), getDesiredStateMessage("NOT ENABLED", timeout));
        return isElementInDesiredCondition(timeout, desiredState);
    }

    @Override
    public boolean waitForNotEnabled() {
        return waitForNotEnabled(getDefaultTimeout());
    }

    private List<WebElement> findElements(long timeout) {
        return ElementFinder.getInstance().findElements(getLocator(), timeout, ElementState.EXISTS_IN_ANY_STATE);
    }

    private By getLocator() {
        return locator;
    }

    private boolean isElementInDesiredCondition(long timeout, DesiredState desiredState){
        return !ElementFinder.getInstance().findElements(locator, timeout, desiredState).isEmpty();
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

    private String getDesiredStateMessage(String desiredStateName, long timeout){
        return String.format(getLocManager().getValue("loc.no.elements.found.in.state"), locator, desiredStateName, timeout);
    }
}
