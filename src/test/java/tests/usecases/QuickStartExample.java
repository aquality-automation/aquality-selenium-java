package tests.usecases;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class QuickStartExample {

    @Test
    public void test(){
        Browser browser = BrowserManager.getBrowser();

        browser.maximize();
        browser.goTo("https://wikipedia.org");
        browser.waitForPageToLoad();

        ElementFactory elementFactory = new ElementFactory();
        ITextBox txbSearch = elementFactory.getTextBox(By.id("searchInput"), "Search");
        txbSearch.type("quality assurance");

        browser.quit();
    }
}
