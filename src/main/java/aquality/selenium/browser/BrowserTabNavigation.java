package aquality.selenium.browser;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.Set;

import static java.lang.String.format;

class BrowserTabNavigation implements IBrowserTabNavigation {

    private final RemoteWebDriver driver;

    private void infoLoc(String key, Object... args) {
        AqualityServices.getLocalizedLogger().info(key, args);
    }

    BrowserTabNavigation(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getCurrentTabHandle() {
        infoLoc("loc.browser.get.tab.handle");
        return getDriver().getWindowHandle();
    }

    @Override
    public Set<String> getTabHandles() {
        infoLoc("loc.browser.get.tab.handles");
        return getDriver().getWindowHandles();
    }

    @Override
    public void switchToTab(String tabHandle, boolean closeCurrent) {
        infoLoc("loc.browser.switch.to.tab.handle", tabHandle);
        closeAndSwitch(tabHandle, closeCurrent);
    }

    @Override
    public void switchToTab(int index, boolean closeCurrent) {
        infoLoc("loc.browser.switch.to.tab.index", index);
        ArrayList<String> handles = new ArrayList<>(getTabHandles());
        if (index < 0 || handles.size() <= index) {
            throw new IndexOutOfBoundsException(format("Index of browser tab '%1$s' you provided is out of range 0..%2$s", index, handles.size()));
        }

        String newTab = handles.get(index);
        closeAndSwitch(newTab, closeCurrent);
    }

    @Override
    public void switchToLastTab(boolean closeCurrent) {
        infoLoc("loc.browser.switch.to.new.tab");
        ArrayList<String> handles = new ArrayList<>(getTabHandles());
        closeAndSwitch(handles.get(handles.size() - 1), closeCurrent);
    }

    @Override
    public void closeTab() {
        infoLoc("loc.browser.tab.close");
        getDriver().close();
    }

    @Override
    public void openNewTab(boolean switchToNew) {
        infoLoc("loc.browser.tab.open.new");
        AqualityServices.getBrowser().executeScript(JavaScript.OPEN_NEW_TAB);
        if (switchToNew) {
            switchToLastTab();
        }
    }

    @Override
    public void openInNewTab(String url) {
        AqualityServices.getBrowser().executeScript(JavaScript.OPEN_IN_NEW_TAB, url);
    }

    private RemoteWebDriver getDriver() {
        return driver;
    }

    private void closeAndSwitch(String name, boolean closeCurrent) {
        if (closeCurrent) {
            closeTab();
        }

        getDriver().switchTo().window(name);
    }
}
