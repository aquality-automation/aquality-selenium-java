package aquality.selenium.browser;

import java.net.URL;
import java.util.Set;

/**
 * Provides functionality to work with browser tab navigation.
 */
public interface IBrowserTabNavigation extends IBrowserWindowNavigation {
    /**
     * Gets current tab handle.
     * @deprecated Use {@link #getCurrentHandle()} instead.
     *
     * @return Current tab handle.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default String getCurrentTabHandle() {
        return getCurrentHandle();
    }

    /**
     * Gets opened tab handles.
     * @deprecated Use {@link #getHandles()} instead.
     *
     * @return Set of tab handles.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default Set<String> getTabHandles() {
        return getHandles();
    }

    /**
     * Switches to tab and doesn't close current tab.
     * @deprecated Use {@link #switchTo(String)} instead.
     *
     * @param tabHandle Tab handle.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToTab(String tabHandle) {
        switchToTab(tabHandle, false);
    }

    /**
     * Switches to tab.
     * @deprecated Use {@link #switchTo(String, boolean)} instead.
     *
     * @param tabHandle    Tab handle.
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToTab(String tabHandle, boolean closeCurrent) {
        switchTo(tabHandle, closeCurrent);
    }

    /**
     * Switches to tab and doesn't close current tab.
     * @deprecated Use {@link #switchTo(int)} instead.
     *
     * @param index Tab index.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToTab(int index) {
        switchToTab(index, false);
    }

    /**
     * Switches to tab.
     * @deprecated Use {@link #switchTo(int, boolean)} instead.
     *
     * @param index        Tab index.
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToTab(int index, boolean closeCurrent) {
        switchTo(index, closeCurrent);
    }

    /**
     * Switches to the last tab and doesn't close current tab.
     * @deprecated Use {@link #switchToLast()} instead.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToLastTab() {
        switchToLastTab(false);
    }

    /**
     * Switches to the last tab.
     * @deprecated Use {@link #switchToLast(boolean)} instead.
     *
     * @param closeCurrent Close current tab if true and leave it otherwise.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void switchToLastTab(boolean closeCurrent) {
        switchToLast(closeCurrent);
    }

    /**
     * Closes current tab.
     * @deprecated Use {@link #close()} instead.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void closeTab() {
        close();
    }

    /**
     * Opens and switches to new tab.
     * @deprecated Use {@link #openNew()} instead.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openNewTab() {
        openNewTab(true);
    }

    /**
     * Opens new tab.
     * @deprecated Use {@link #openNew(boolean)} instead.
     *
     * @param switchToNew Switches to new tab if true and stays at current otherwise.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openNewTab(boolean switchToNew) {
        openNew(switchToNew);
    }

    /**
     * Opens and switches to new tab using JS function.
     * @deprecated Use {@link #openNewViaJs()} instead.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openNewTabViaJs() {
        openNewTabViaJs(true);
    }

    /**
     * Opens new tab using JS function.
     * @deprecated Use {@link #openNewViaJs(boolean)} instead.
     *
     * @param switchToNew Switches to new tab if true and stays at current otherwise.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openNewTabViaJs(boolean switchToNew) {
        openNewViaJs(switchToNew);
    }

    /**
     * Navigates to desired url in new tab.
     * @deprecated Use {@link #openInNew(String)} instead.
     *
     * @param url String representation of URL.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openInNewTab(String url) {
        openInNew(url);
    }

    /**
     * Navigates to desired url in new tab.
     * @deprecated Use {@link #openInNew(URL)} instead.
     *
     * @param url target URL.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openInNewTab(URL url) {
        openInNew(url);
    }

    /**
     * Navigates to desired url in new tab.
     * @deprecated Use {@link #openInNewViaJs(String)} instead.
     *
     * @param url String representation of URL.
     */
    @Deprecated(since = "4.5.0", forRemoval = true)
    default void openInNewTabViaJs(String url) {
        openInNewViaJs(url);
    }
}
