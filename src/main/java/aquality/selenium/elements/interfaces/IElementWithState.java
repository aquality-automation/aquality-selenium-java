package aquality.selenium.elements.interfaces;

import aquality.selenium.configuration.Configuration;

public interface IElementWithState extends IElementStateProvider {
    /**
     * Provides ability to define of element's state (whether it is displayed, exists or not) and respective waiting functions
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


    default boolean isExist() {
        return state().isExist();
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


    default boolean isEnabled() {
        return state().isEnabled();
    }

    default boolean waitForEnabled(long timeout) {
        return state().waitForEnabled(timeout);
    }

    default boolean waitForEnabled() {
        return waitForEnabled(getDefaultTimeout());
    }

    default boolean waitForNotEnabled(long timeout) {
        return state().waitForNotEnabled(timeout);
    }

    default boolean waitForNotEnabled() {
        return waitForNotEnabled(getDefaultTimeout());
    }
}
