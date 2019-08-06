package aquality.selenium.elements.interfaces;

/**
 * Provides ability to define of element's state (whether it is displayed, exist or not)
 * Also provides respective positive and negative waiting functions
 */
public interface IElementStateProvider {

    /**
     * Is an element displayed on the page.
     * @return true if element displayed, false otherwise
     */
    boolean isDisplayed();

    /**
     * Is an element exist in DOM (without visibility check)
     * @return true if element exist, false otherwise
     */
    boolean isExist();

    /**
     * Waits for is element displayed on the page.
     * @param timeout Timeout for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForDisplayed(long timeout);

    /**
     * Waits until element is exist in DOM (without visibility check).
     * @param timeout Timeout for waiting
     * @return true if element exist after waiting, false otherwise
     */
    boolean waitForExist(long timeout);

    /**
     * Waits for is element displayed on the page.
     * @param timeout Timeout for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForNotDisplayed(long timeout);

    /**
     * Waits until element does not exist in DOM (without visibility check).
     *
     * @return true if element does not exist after waiting, false otherwise
     */
    boolean waitForNotExist(long timeout);
}
