package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.elements.interfaces.IElementFactory;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import theinternet.TheInternetPage;

import java.io.IOException;

import static utils.FileUtil.getResourceFileByName;

public abstract class BaseTest {
    private static final String DEFAULT_URL = TheInternetPage.LOGIN.getAddress();

    protected final Dimension defaultSize = new Dimension(1024, 768);
    protected final IElementFactory elementFactory;

    protected BaseTest() {
        elementFactory = AqualityServices.getElementFactory();
    }

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
        }
    }

    protected void navigate(TheInternetPage page) {
        getBrowser().goTo(page.getAddress());
    }

    protected Browser getBrowser() {
        return AqualityServices.getBrowser();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getScriptResultOrDefault(String scriptName, T defaultValue, Object... arguments) {
        try {
            return ((T) getBrowser().executeScript(getResourceFileByName(scriptName), arguments));
        } catch (IOException e) {
            return defaultValue;
        }
    }
}
