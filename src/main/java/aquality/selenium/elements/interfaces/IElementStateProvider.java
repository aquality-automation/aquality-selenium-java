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
     * Waits for is element displayed on the page.
     * @param timeout Timeout for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForDisplayed(long timeout);

    /**
     * Waits for is element displayed on the page.
     * Uses condition timeout from settings file for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForDisplayed();

    /**
     * Waits for is element displayed on the page.
     * @param timeout Timeout for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForNotDisplayed(long timeout);


    /**
     * Waits for is element displayed on the page.
     * Uses condition timeout from settings file for waiting
     * @return true if element displayed after waiting, false otherwise
     */
    boolean waitForNotDisplayed();

    /**
     * Is an element exist in DOM (without visibility check)
     * @return true if element exist, false otherwise
     */
    boolean isExist();

    /**
     * Waits until element is exist in DOM (without visibility check).
     * @param timeout Timeout for waiting
     * @return true if element exist after waiting, false otherwise
     */
    boolean waitForExist(long timeout);


    /**
     * Waits until element is exist in DOM (without visibility check).
     * Uses condition timeout from settings file for waiting
     * @return true if element exist after waiting, false otherwise
     */
    boolean waitForExist();

    /**
     * Waits until element does not exist in DOM (without visibility check).
     *
     * @return true if element does not exist after waiting, false otherwise
     */
    boolean waitForNotExist(long timeout);

    /**
     * Waits until element does not exist in DOM (without visibility check).
     * Uses condition timeout from settings file for waiting
     * @return true if element does not exist after waiting, false otherwise
     */
    boolean waitForNotExist();

    /**
     * Check that the element is enabled (performed by a class member)
     *
     * @return true if enabled
     */
    boolean isEnabled();

    /**
     * Check that the element is enabled (performed by a class member)
     *
     * @param timeout Timeout for waiting
     * @return true if enabled
     */
    boolean waitForEnabled(long timeout);


    /**
     * Check that the element is enabled (performed by a class member)
     * Uses condition timeout from settings file for waiting
     * @return true if enabled
     */
    boolean waitForEnabled();

    /**
     * Waits until element does not enabled in DOM
     *
     * @return true if element does not enabled after waiting, false otherwise
     */
    boolean waitForNotEnabled(long timeout);

    /**
     * Waits until element does not enabled in DOM
     * Uses condition timeout from settings file for waiting
     * @return true if element does not enabled after waiting, false otherwise
     */
    boolean waitForNotEnabled();
}
