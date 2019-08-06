package tests;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.interfaces.IElementFactory;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import theinternet.TheInternetPage;

public abstract class BaseTest {
    private static final String DEFAULT_URL = TheInternetPage.LOGIN.getAddress();

    protected final Dimension defaultSize = new Dimension(1024, 768);
    protected final IElementFactory elementFactory;

    protected BaseTest() {
        elementFactory = new ElementFactory();
    }

    @BeforeMethod
    protected void beforeMethod() {
        BrowserManager.getBrowser().goTo(DEFAULT_URL);
        BrowserManager.getBrowser().setSize(defaultSize.width, defaultSize.height);
    }

    @AfterMethod
    public void afterTest(){
        BrowserManager.getBrowser().quit();
    }
}
