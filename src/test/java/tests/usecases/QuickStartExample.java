package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class QuickStartExample {

    @Test
    public void test(){
        Browser browser = AqualityServices.getBrowser();

        browser.maximize();
        browser.goTo("https://wikipedia.org");
        browser.waitForPageToLoad();

        IElementFactory elementFactory = AqualityServices.getElementFactory();
        ITextBox txbSearch = elementFactory.getTextBox(By.id("searchInput"), "Search");
        txbSearch.type("quality assurance");
        txbSearch.submit();
        browser.waitForPageToLoad();

        browser.quit();
    }
}
