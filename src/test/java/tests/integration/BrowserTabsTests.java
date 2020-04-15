package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import java.util.ArrayList;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertFalse;

public class BrowserTabsTests extends BaseTest {

    private final WelcomeForm welcomeForm = new WelcomeForm();

    @Override
    @BeforeMethod
    public void beforeMethod() {
        AqualityServices.getBrowser().goTo(welcomeForm.getUrl());
    }

    @Test
    public void testShouldBePossibleToOpenUrlInNewTab() {
        String url = welcomeForm.getUrl();
        Browser browser = AqualityServices.getBrowser();
        browser.tabs().openInNewTab(url);
        browser.tabs().switchToLastTab();
        assertEquals(2, browser.tabs().getTabHandles().size());
        assertEquals(browser.getCurrentUrl(), url + "/", "Url should be opened in new tab");
    }

    @Test
    public void testShouldBePossibleToHandleTab() {
        Browser browser = AqualityServices.getBrowser();
        String tabHandle = browser.tabs().getCurrentTabHandle();
        assertFalse(tabHandle.isEmpty(), "Tab handle should not be empty");
    }

    @Test
    public void testShouldBePossibleToGetTabHandles() {
        Browser browser = AqualityServices.getBrowser();
        Set<String> tabHandles = browser.tabs().getTabHandles();
        assertEquals(1, tabHandles.size(), "Tab number should be correct");
        assertFalse(tabHandles.stream().findFirst().toString().isEmpty(), "Tab handle should not be empty");
    }

    @Test
    public void testShouldBePossibleToOpenNewTab() {
        Browser browser = AqualityServices.getBrowser();
        String tabHandle = browser.tabs().getCurrentTabHandle();

        browser.tabs().openNewTab();
        String newTabHandle = browser.tabs().getCurrentTabHandle();
        assertEquals(2, browser.tabs().getTabHandles().size(), "New tab should be opened");
        assertNotEquals(tabHandle, newTabHandle, "Browser should be switched to new tab");

        browser.tabs().openNewTab(false);
        assertEquals(3, browser.tabs().getTabHandles().size(), "New tab should be opened");
        assertEquals(newTabHandle, browser.tabs().getCurrentTabHandle(), "Browser should not be switched to new tab");
    }

    @Test
    public void testShouldBePossibleToCloseTab() {
        Browser browser = AqualityServices.getBrowser();
        welcomeForm.clickElementalSelenium();
        assertEquals(2, browser.tabs().getTabHandles().size(), "New tab should be opened");
        browser.tabs().closeTab();
        assertEquals(1, browser.tabs().getTabHandles().size(), "New tab should be closed");
    }

    @Test
    public void testShouldBePossibleToSwitchToNewTab() {
        checkSwitching(2, () -> AqualityServices.getBrowser().tabs().switchToLastTab());
    }

    @Test
    public void testShouldBePossibleToSwitchToNewTabAndClose() {
        checkSwitching(1, () -> AqualityServices.getBrowser().tabs().switchToLastTab(true));
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByHandle() {
        checkSwitching(3, () -> {
            Browser browser = AqualityServices.getBrowser();
            String tabHandle = getLastTab();
            browser.tabs().openNewTab(false);
            browser.tabs().switchToTab(tabHandle);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByHandleAndClose() {
        checkSwitching(2, () -> {
            Browser browser = AqualityServices.getBrowser();
            String tabHandle = getLastTab();
            browser.tabs().openNewTab(false);
            browser.tabs().switchToTab(tabHandle, true);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByIndex() {
        checkSwitching(3, () -> {
            AqualityServices.getBrowser().tabs().openNewTab(false);
            AqualityServices.getBrowser().tabs().switchToTab(1);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByIndexAndClose() {
        checkSwitching(2, () -> {
            AqualityServices.getBrowser().tabs().openNewTab(false);
            AqualityServices.getBrowser().tabs().switchToTab(1, true);
        });
    }

    @Test
    public void testShouldThrowIfSwitchToNewTabByIncorrectIndex() {
        Assert.expectThrows(IndexOutOfBoundsException.class, () -> AqualityServices.getBrowser().tabs().switchToTab(10, true));
    }

    private void checkSwitching(int expectedTabCount, Runnable switchMethod) {
        Browser browser = AqualityServices.getBrowser();
        welcomeForm.clickElementalSelenium();
        String newTabHandle = getLastTab();
        switchMethod.run();
        assertEquals(newTabHandle, browser.tabs().getCurrentTabHandle(), "Browser should be switched to correct tab");
        assertEquals(expectedTabCount, browser.tabs().getTabHandles().size(), "Number of tabs should be correct");
    }

    private String getLastTab() {
        ArrayList<String> tabs = new ArrayList<>(AqualityServices.getBrowser().tabs().getTabHandles());
        return tabs.get(tabs.size() - 1);
    }
}