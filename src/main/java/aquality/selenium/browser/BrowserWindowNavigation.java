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
 * Wrapper to work with browser tab/window navigation with localized logging.
 */
public class BrowserWindowNavigation implements IBrowserTabNavigation {

    private final RemoteWebDriver driver;
    private final ILocalizedLogger logger;
    private final WindowType windowType;
    private final String type;

    protected BrowserWindowNavigation(RemoteWebDriver driver, ILocalizedLogger logger, WindowType windowType) {
        this.driver = driver;
        this.logger = logger;
        this.windowType = windowType;
        type = windowType.name().toLowerCase();
    }

    private void logInfo(String key, Object... params) {
        logger.info(String.format(key, type), params);
    }

    @Override
    public String getCurrentHandle() {
        logInfo("loc.browser.get.%s.handle");
        return driver.getWindowHandle();
    }

    @Override
    public Set<String> getHandles() {
        logInfo("loc.browser.get.%s.handles");
        return driver.getWindowHandles();
    }

    @Override
    public void switchTo(final String handle, boolean closeCurrent) {
        logInfo("loc.browser.switch.to.%s.handle", handle);
        closeAndSwitch(handle, closeCurrent);
    }

    @Override
    public void switchTo(int index, boolean closeCurrent) {
        logInfo("loc.browser.switch.to.%s.index", index);
        List<String> handles = new ArrayList<>(getHandles());
        if (index < 0 || handles.size() <= index) {
            throw new IndexOutOfBoundsException(format("Index of browser %1$s '%2$s' you provided is out of range 0..%3$s", type, index, handles.size()));
        }

        String newTab = handles.get(index);
        closeAndSwitch(newTab, closeCurrent);
    }

    @Override
    public void switchToLast(boolean closeCurrent) {
        logInfo("loc.browser.switch.to.new.%s");
        List<String> handles = new ArrayList<>(getHandles());
        closeAndSwitch(handles.get(handles.size() - 1), closeCurrent);
    }

    @Override
    public void close() {
        logInfo("loc.browser.%s.close");
        driver.close();
    }

    @Override
    public void openNew(boolean switchToNew) {
        openNew(switchToNew, true);
    }
    private void openNew(boolean switchToNew, boolean log) {
        if (log) {
            logInfo("loc.browser.%s.open.new");
        }
        String currentHandle = switchToNew ? null : getCurrentHandle();
        driver.switchTo().newWindow(windowType);
        if (!switchToNew) {
            closeAndSwitch(currentHandle, false);
        }
    }

    @Override
    public void openNewViaJs(boolean switchToNew) {
        logInfo("loc.browser.%s.open.new");
        JavaScript script = WindowType.TAB == windowType ? JavaScript.OPEN_NEW_TAB : JavaScript.OPEN_NEW_WINDOW;
        driver.executeScript(script.getScript());
        if (switchToNew) {
            switchToLast();
        }
    }

    @Override
    public void openInNew(final String url) {
        logInfo("loc.browser.navigate.in.new.%s", url);
        openNew(true, false);
        driver.navigate().to(url);
    }

    @Override
    public void openInNew(final URL url) {
        logInfo("loc.browser.navigate.in.new.%s", url);
        driver.switchTo().newWindow(windowType);
        driver.navigate().to(url);
    }

    @Override
    public void openInNewViaJs(final String url) {
        JavaScript script = WindowType.TAB == windowType ? JavaScript.OPEN_IN_NEW_TAB : JavaScript.OPEN_IN_NEW_WINDOW;
        driver.executeScript(script.getScript(), url);
    }

    private void closeAndSwitch(final String name, boolean closeCurrent) {
        if (closeCurrent) {
            close();
        }

        driver.switchTo().window(name);
    }
}
