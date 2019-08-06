package aquality.selenium.elements.interfaces;

import aquality.selenium.configuration.Configuration;

public interface IElementWithState extends IElementStateProvider {
    /**
     * Provides ability to define of element's state (whether it is displayed, exist or not) and respective waiting functions
     * @return provider to define element's state
     */
    IElementStateProvider state();

    /**
     * @return default timeout of wait for state
     */
    default long getDefaultTimeout() {
        return Configuration.getInstance().getTimeoutConfiguration().getCondition();
    }

    default boolean isDisplayed() {
        return state().isDisplayed();
    }

    default boolean isExist() {
        return state().isExist();
    }

    default boolean waitForDisplayed(long timeout) {
        return state().waitForDisplayed(timeout);
    }

    default boolean waitForDisplayed() {
        return waitForDisplayed(getDefaultTimeout());
    }

    default boolean waitForNotDisplayed(long timeout) {
        return state().waitForNotDisplayed(timeout);
    }

    default boolean waitForNotDisplayed() {
        return waitForNotDisplayed(getDefaultTimeout());
    }

    default boolean waitForExist(long timeout) {
        return state().waitForExist(timeout);
    }

    default boolean waitForExist() {
        return waitForExist(getDefaultTimeout());
    }

    default boolean waitForNotExist(long timeout) {
        return state().waitForNotExist(timeout);
    }

    default boolean waitForNotExist() {
        return waitForNotExist(getDefaultTimeout());
    }

    /**
     * Checks element's class attribute to contain specified className
     * @param className ClassName parameter
     * @return true if required className contained in class attribute, false otherwise
     */
    boolean hasState(String className);
}
