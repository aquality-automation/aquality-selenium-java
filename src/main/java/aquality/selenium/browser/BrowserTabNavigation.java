package aquality.selenium.browser;

import aquality.selenium.core.localization.ILocalizedLogger;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

/**
 * Wrapper to work with browser tab navigation with localized logging.
 */
public class BrowserTabNavigation implements IBrowserTabNavigation {

    private final RemoteWebDriver driver;
    private final ILocalizedLogger logger;

    protected BrowserTabNavigation(RemoteWebDriver driver, ILocalizedLogger logger) {
        this.driver = driver;
        this.logger = logger;
    }

    @Override
    public String getCurrentTabHandle() {
        logger.info("loc.browser.get.tab.handle");
        return driver.getWindowHandle();
    }

    @Override
    public Set<String> getTabHandles() {
        logger.info("loc.browser.get.tab.handles");
        return driver.getWindowHandles();
    }

    @Override
    public void switchToTab(final String tabHandle, boolean closeCurrent) {
        logger.info("loc.browser.switch.to.tab.handle", tabHandle);
        closeAndSwitch(tabHandle, closeCurrent);
    }

    @Override
    public void switchToTab(int index, boolean closeCurrent) {
        logger.info("loc.browser.switch.to.tab.index", index);
        List<String> handles = new ArrayList<>(getTabHandles());
        if (index < 0 || handles.size() <= index) {
            throw new IndexOutOfBoundsException(format("Index of browser tab '%1$s' you provided is out of range 0..%2$s", index, handles.size()));
        }

        String newTab = handles.get(index);
        closeAndSwitch(newTab, closeCurrent);
    }

    @Override
    public void switchToLastTab(boolean closeCurrent) {
        logger.info("loc.browser.switch.to.new.tab");
        List<String> handles = new ArrayList<>(getTabHandles());
        closeAndSwitch(handles.get(handles.size() - 1), closeCurrent);
    }

    @Override
    public void closeTab() {
        logger.info("loc.browser.tab.close");
        driver.close();
    }

    @Override
    public void openNewTab(boolean switchToNew) {
        logger.info("loc.browser.tab.open.new");
        String currentHandle = switchToNew ? null : getCurrentTabHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        if (!switchToNew) {
            closeAndSwitch(currentHandle, false);
        }
    }

    @Override
    public void openNewTabViaJs(boolean switchToNew) {
        logger.info("loc.browser.tab.open.new");
        driver.executeScript(JavaScript.OPEN_NEW_TAB.getScript());
        if (switchToNew) {
            switchToLastTab();
        }
    }

    @Override
    public void openInNewTab(final String url) {
        openNewTab();
        driver.navigate().to(url);
    }

    @Override
    public void openInNewTab(final URL url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(url);
    }

    @Override
    public void openInNewTabViaJs(final String url) {
        driver.executeScript(JavaScript.OPEN_IN_NEW_TAB.getScript(), url);
    }

    private void closeAndSwitch(final String name, boolean closeCurrent) {
        if (closeCurrent) {
            closeTab();
        }

        driver.switchTo().window(name);
    }
}
