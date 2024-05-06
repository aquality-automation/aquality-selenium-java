package aquality.selenium.browser;

import java.net.URL;
import java.util.Set;

/**
 * Provides functionality to work with browser tab/window navigation.
 */
public interface IBrowserWindowNavigation {
    /**
     * Gets current tab/window handle.
     *
     * @return Current tab/window handle.
     */
    String getCurrentHandle();

    /**
     * Gets opened tab/window handles.
     *
     * @return Set of tab/window handles.
     */
    Set<String> getHandles();

    /**
     * Switches to tab/window and doesn't close current tab/window.
     *
     * @param handle tab/window handle.
     */
    default void switchTo(String handle) {
        switchTo(handle, false);
    }

    /**
     * Switches to tab/window.
     *
     * @param handle    tab/window handle.
     * @param closeCurrent Close current tab/window if true and leave it otherwise.
     */
    void switchTo(String handle, boolean closeCurrent);

    /**
     * Switches to tab/window and doesn't close current tab/window.
     *
     * @param index tab/window index.
     */
    default void switchTo(int index) {
        switchTo(index, false);
    }

    /**
     * Switches to tab/window.
     *
     * @param index        tab/window index.
     * @param closeCurrent Close current tab/window if true and leave it otherwise.
     */
    void switchTo(int index, boolean closeCurrent);

    /**
     * Switches to the last tab/window and doesn't close current tab/window.
     */
    default void switchToLast() {
        switchToLast(false);
    }

    /**
     * Switches to the last tab/window.
     *
     * @param closeCurrent Close current tab/window if true and leave it otherwise.
     */
    void switchToLast(boolean closeCurrent);

    /**
     * Closes current tab/window.
     */
    void close();

    /**
     * Opens and switches to new tab/window.
     */
    default void openNew() {
        openNew(true);
    }

    /**
     * Opens new tab/window.
     *
     * @param switchToNew Switches to new tab/window if true and stays at current otherwise.
     */
    void openNew(boolean switchToNew);

    /**
     * Opens and switches to new tab/window using JS function.
     */
    default void openNewViaJs() {
        openNewViaJs(true);
    }

    /**
     * Opens new tab/window using JS function.
     *
     * @param switchToNew Switches to new tab/window if true and stays at current otherwise.
     */
    void openNewViaJs(boolean switchToNew);

    /**
     * Navigates to desired url in new tab/window.
     *
     * @param url String representation of URL.
     */
    void openInNew(String url);

    /**
     * Navigates to desired url in new tab/window.
     *
     * @param url target URL.
     */
    void openInNew(URL url);

    /**
     * Navigates to desired url in new tab/window.
     *
     * @param url String representation of URL.
     */
    void openInNewViaJs(String url);
}
