package aquality.selenium.elements;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.elements.interfaces.IElementFinder;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class ElementFinder implements IElementFinder {
    private static final ThreadLocal<ElementFinder> instanceHolder = new ThreadLocal<>();

    static ElementFinder getInstance() {
        if(instanceHolder.get() == null){
            instanceHolder.set(new ElementFinder());
        }
        return instanceHolder.get();
    }

    private ElementFinder(){
    }

    @Override
    public long getDefaultTimeout() {
        return getTimeoutConfiguration().getCondition();
    }

    @Override
    public WebElement findElement(By locator, long timeout, ElementState state) {
        List<WebElement> elements = findElements(locator, timeout, state);
        if(!elements.isEmpty()){
            return elements.get(0);
        }else {
            String message = String.format(
                    "element was not found in %d seconds in state %s by locator %s", timeout, state, locator);
            throw new NoSuchElementException(message);
        }
    }

    @Override
    public List<WebElement> findElements(By locator, long timeout, ElementState state) {
        switch (state) {
            case DISPLAYED:
                return findElements(locator, timeout, new DesiredState(WebElement::isDisplayed, String.format(getLocManager().getValue("loc.no.elements.found.in.state"), locator, "DISPLAYED", timeout)).withCatchingTimeoutException());
            case EXISTS_IN_ANY_STATE:
                return findElements(locator, timeout, new DesiredState(Objects::nonNull, String.format(getLocManager().getValue("loc.no.elements.found.in.state"), locator, "EXIST", timeout)).withCatchingTimeoutException());
            default:
                String errorMessage = String.format("'%s' state is not recognized", state.toString());
                throw new IllegalArgumentException(errorMessage);
        }
    }

    List<WebElement> findElements(By locator, long timeout, DesiredState desiredState)
    {
        List<WebElement> foundElements = new ArrayList<>();
        List<WebElement> resultElements = new ArrayList<>();
        long zeroTimeout = 0L;
        getBrowser().setImplicitWaitTimeOut(zeroTimeout);
        try{
            WebDriverWait webDriverWait = new WebDriverWait(getBrowser().getDriver(), timeout);
            webDriverWait.until(driver ->
            {
                List<WebElement> allFoundElements = driver.findElements(locator);
                foundElements.addAll(allFoundElements);
                List<WebElement> filteredElements = filterByState(allFoundElements, desiredState.getDesiredStatePredicate());
                resultElements.addAll(filteredElements);
                return !filteredElements.isEmpty();
            });
        }catch (TimeoutException e){
            applyResult(locator, desiredState, foundElements);
        }
        getBrowser().setImplicitWaitTimeOut(getTimeoutConfiguration().getImplicit());
        return resultElements;
    }

    /**
     * depends on configuration of DesiredState object it can be required to throw or not NoSuchElementException
     * @param locator locator that is using to find elements
     * @param desiredState DesiredState object
     * @param foundElements list of all found elements by locator.
     */
    private void applyResult(By locator, DesiredState desiredState, List<WebElement> foundElements){
        if (desiredState.isCatchingInTimeoutException()){
            if(foundElements.isEmpty()){
                String message = String.format(getLocManager().getValue("loc.no.elements.found.by.locator"), locator);
                if(desiredState.isThrowingNoSuchElementException()){
                    throw new NoSuchElementException(message);
                }
                getLogger().debug(message);
            }else {
                getLogger().debug(String.format(getLocManager().getValue("loc.elements.were.found.but.not.in.state"), locator, desiredState.getMessage()));
            }
        }else {
            throw new TimeoutException(desiredState.getMessage());
        }
    }

    /**
     * tries to find list of elements in the DOM during defined timeout
     * @param locator locator to find elements
     * @param timeout timeout
     * @return list of elements ot empty list if no elements found
     */
    public List<WebElement> findElements(By locator, long timeout) {
        return findElements(locator, timeout, ElementState.EXISTS_IN_ANY_STATE);
    }

    private List<WebElement> filterByState(List<WebElement> foundElements, Predicate<WebElement> desiredElementState){
        List<WebElement> filteredElements = new ArrayList<>();
        if(!foundElements.isEmpty()){
            filteredElements.addAll(foundElements.stream().filter(desiredElementState).collect(Collectors.toList()));
        }
        return filteredElements;
    }

    private Browser getBrowser() {
        return BrowserManager.getBrowser();
    }

    private ITimeoutConfiguration getTimeoutConfiguration() {
        return Configuration.getInstance().getTimeoutConfiguration();
    }

    private Logger getLogger(){
        return Logger.getInstance();
    }

    private LocalizationManager getLocManager(){
        return LocalizationManager.getInstance();
    }
}
