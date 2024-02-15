package aquality.selenium.browser;

import java.net.URL;
import java.util.Set;

/**
 * Provides functionality to work with browser tab navigation.
 */
public interface IBrowserTabNavigation {
    /**
     * Gets current tab handle.
     *
     * @return Current tab handle.
     */
    String getCurrentTabHandle();

    /**
     * Gets opened tab handles.
     *
     * @return Set of tab handles.
     */
    Set<String> getTabHandles();

    /**
     * Switches to tab and doesn't close current tab.
     *
     * @param tabHandle Tab handle.
     */
    default void switchToTab(String tabHandle) {
        switchToTab(tabHandle, false);
    }

    /**
     * Switches to tab.
     *
     * @param tabHandle    Tab handle.
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    void switchToTab(String tabHandle, boolean closeCurrent);

    /**
     * Switches to tab and doesn't close current tab.
     *
     * @param index Tab index.
     */
    default void switchToTab(int index) {
        switchToTab(index, false);
    }

    /**
     * Switches to tab.
     *
     * @param index        Tab index.
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    void switchToTab(int index, boolean closeCurrent);

    /**
     * Switches to the last tab and doesn't close current tab.
     */
    default void switchToLastTab() {
        switchToLastTab(false);
    }

    /**
     * Switches to the last tab.
     *
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    void switchToLastTab(boolean closeCurrent);

    /**
     * Closes current tab.
     */
    void closeTab();

    /**
     * Opens and switches to new tab.
     */
    default void openNewTab() {
        openNewTab(true);
    }

    /**
     * Opens new tab.
     *
     * @param switchToNew Switches to new tab if true and stays at current otherwise.
     */
    void openNewTab(boolean switchToNew);

    /**
     * Opens and switches to new tab using JS function.
     */
    default void openNewTabViaJs() {
        openNewTabViaJs(true);
    }

    /**
     * Opens new tab using JS function.
     *
     * @param switchToNew Switches to new tab if true and stays at current otherwise.
     */
    void openNewTabViaJs(boolean switchToNew);

    /**
     * Navigates to desired url in new tab.
     *
     * @param url String representation of URL.
     */
    void openInNewTab(String url);

    /**
     * Navigates to desired url in new tab.
     *
     * @param url target URL.
     */
    void openInNewTab(URL url);

    /**
     * Navigates to desired url in new tab.
     *
     * @param url String representation of URL.
     */
    void openInNewTabViaJs(String url);
}
