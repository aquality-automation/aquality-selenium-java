package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.IBrowserWindowNavigation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.WelcomeForm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public class BrowserTabsTests extends BaseTest {

    private final WelcomeForm welcomeForm = new WelcomeForm();

    protected IBrowserWindowNavigation tabs() {
        return AqualityServices.getBrowser().tabs();
    }

    @Override
    @BeforeMethod
    public void beforeMethod() {
        AqualityServices.getBrowser().goTo(welcomeForm.getUrl());
    }

    @Test
    public void testShouldBePossibleToOpenUrlInNewTab() {
        String url = welcomeForm.getUrl();
        tabs().openInNew(url);
        assertEquals(2, tabs().getHandles().size());
        assertEquals(AqualityServices.getBrowser().getCurrentUrl(), url + "/", "Url should be opened in new tab");
    }

    @Test
    public void testShouldBePossibleToOpenUriInNewTab() throws MalformedURLException {
        URL url = new URL(welcomeForm.getUrl());
        tabs().openInNew(url);
        assertEquals(2, tabs().getHandles().size());
        assertEquals(AqualityServices.getBrowser().getCurrentUrl(), url + "/", "Url should be opened in new tab");
    }

    @Test
    public void testShouldBePossibleToOpenUrlInNewTabViaJs() {
        String url = welcomeForm.getUrl();
        tabs().openInNewViaJs(url);
        assertEquals(2, tabs().getHandles().size());
        assertEquals(AqualityServices.getBrowser().getCurrentUrl(), url + "/", "Url should be opened in new tab");
    }

    @Test
    public void testShouldBePossibleToHandleTab() {
        String tabHandle = tabs().getCurrentHandle();
        assertFalse(tabHandle.isEmpty(), "Tab handle should not be empty");
    }

    @Test
    public void testShouldBePossibleToGetTabHandles() {
        Set<String> tabHandles = tabs().getHandles();
        assertEquals(1, tabHandles.size(), "Tab number should be correct");
        assertFalse(tabHandles.stream().findFirst().toString().isEmpty(), "Tab handle should not be empty");
    }

    @Test
    public void testShouldBePossibleToOpenNewTab() {
        String tabHandle = tabs().getCurrentHandle();

        tabs().openNew();
        String newTabHandle = tabs().getCurrentHandle();
        assertEquals(2, tabs().getHandles().size(), "New tab should be opened");
        assertNotEquals(tabHandle, newTabHandle, "Browser should be switched to new tab");

        tabs().openNew(false);
        assertEquals(3, tabs().getHandles().size(), "New tab should be opened");
        assertEquals(newTabHandle, tabs().getCurrentHandle(), "Browser should not be switched to new tab");
    }

    @Test
    public void testShouldBePossibleToOpenNewTabViaJs() {
        String tabHandle = tabs().getCurrentHandle();

        tabs().openNewViaJs();
        String newTabHandle = tabs().getCurrentHandle();
        assertEquals(2, tabs().getHandles().size(), "New tab should be opened");
        assertNotEquals(tabHandle, newTabHandle, "Browser should be switched to new tab");

        tabs().openNewViaJs(false);
        assertEquals(3, tabs().getHandles().size(), "New tab should be opened");
        assertEquals(newTabHandle, tabs().getCurrentHandle(), "Browser should not be switched to new tab");
    }

    @Test
    public void testShouldBePossibleToCloseTab() {
        welcomeForm.clickElementalSelenium();
        assertEquals(2, tabs().getHandles().size(), "New tab should be opened");
        tabs().close();
        assertEquals(1, tabs().getHandles().size(), "New tab should be closed");
    }

    @Test
    public void testShouldBePossibleToSwitchToNewTab() {
        checkSwitching(2, () -> tabs().switchToLast());
    }

    @Test
    public void testShouldBePossibleToSwitchToNewTabAndClose() {
        checkSwitching(1, () -> tabs().switchToLast(true));
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByHandle() {
        checkSwitching(3, () -> {
            String tabHandle = getLastTab();
            tabs().openNew(false);
            tabs().switchTo(tabHandle);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByHandleAndClose() {
        checkSwitching(2, () -> {
            String tabHandle = getLastTab();
            tabs().openNew(false);
            tabs().switchTo(tabHandle, true);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByIndex() {
        checkSwitching(3, () -> {
            tabs().openNew(false);
            tabs().switchTo(1);
        });
    }

    @Test
    public void testShouldBePossibleToSwitchToTabByIndexAndClose() {
        checkSwitching(2, () -> {
            tabs().openNew(false);
            tabs().switchTo(1, true);
        });
    }

    @Test
    public void testShouldThrowIfSwitchToNewTabByIncorrectIndex() {
        Assert.expectThrows(IndexOutOfBoundsException.class, () -> tabs().switchTo(10, true));
    }

    private void checkSwitching(int expectedTabCount, Runnable switchMethod) {
        welcomeForm.clickElementalSelenium();
        String newTabHandle = getLastTab();
        switchMethod.run();
        assertEquals(newTabHandle, tabs().getCurrentHandle(), "Browser should be switched to correct tab");
        assertEquals(expectedTabCount, tabs().getHandles().size(), "Number of tabs should be correct");
    }

    private String getLastTab() {
        List<String> tabs = new ArrayList<>(tabs().getHandles());
        return tabs.get(tabs.size() - 1);
    }
}
