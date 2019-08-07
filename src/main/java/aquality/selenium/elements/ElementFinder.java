package aquality.selenium.elements;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.configuration.ITimeoutConfiguration;
import aquality.selenium.elements.interfaces.IElementFinder;
import aquality.selenium.waitings.ConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

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
        List<WebElement> resultElements = new ArrayList<>();
        long zeroTimeout = 0L;
        getBrowser().setImplicitlyWaitTimeout(zeroTimeout);
        ConditionalWait.waitForTrue(y -> {
            List<WebElement> foundElements = getBrowser().getDriver().findElements(locator);
            List<WebElement> filteredElements = filterByState(foundElements, state);
            resultElements.addAll(filteredElements);
            return !filteredElements.isEmpty();
        }, timeout);
        getBrowser().setImplicitlyWaitTimeout(getTimeoutConfiguration().getImplicit());
        return resultElements;
    }

    private List<WebElement> filterByState(List<WebElement> foundElements, ElementState state){
        List<WebElement> filteredElements = new ArrayList<>();
        if(!foundElements.isEmpty()){
            for (WebElement foundElement : foundElements) {
                switch (state) {
                    case DISPLAYED:
                        if (foundElement.isDisplayed()) {
                            filteredElements.add(foundElement);
                        }
                        break;
                    case EXISTS_IN_ANY_STATE:
                        filteredElements.add(foundElement);
                        break;
                    default:
                        String errorMessage = String.format("'%s' state is not recognized", state.toString());
                        throw new IllegalArgumentException(errorMessage);
                }
            }
        }
        return filteredElements;
    }

    private Browser getBrowser() {
        return BrowserManager.getBrowser();
    }

    private ITimeoutConfiguration getTimeoutConfiguration() {
        return Configuration.getInstance().getTimeoutConfiguration();
    }
}
