package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.IBrowserWindowNavigation;

public class BrowserWindowsTests extends BrowserTabsTests {
    @Override
    protected IBrowserWindowNavigation tabs() {
        return AqualityServices.getBrowser().windows();
    }
}
