package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.ElementState;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Provides ability to find elements in desired ElementState
 */
public interface IElementFinder extends SearchContext {

    /**
     * Finds elements in desired ElementState
     * @param locator elements locator
     * @param timeout timeout for search
     * @param state desired ElementState
     * @return list of found elements
     */
    List<WebElement> findElements(By locator, long timeout, ElementState state);

    /**
     * Finds element in desired ElementState
     * @param locator elements locator
     * @param timeout timeout for search
     * @param state desired ElementState
     * @throws org.openqa.selenium.NoSuchElementException if element was not found in time in desired state
     * @return found element
     */
    WebElement findElement(By locator, long timeout, ElementState state);

    default List<WebElement> findElements(By by) {
        return findElements(by, getDefaultTimeout(), ElementState.EXISTS_IN_ANY_STATE);
    }

    default WebElement findElement(By by) {
        return findElement(by, getDefaultTimeout(), ElementState.EXISTS_IN_ANY_STATE);
    }

    /**
     * @return default timeout for element waiting
     */
    long getDefaultTimeout();
}
