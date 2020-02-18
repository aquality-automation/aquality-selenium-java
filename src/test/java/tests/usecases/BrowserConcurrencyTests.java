package tests.usecases;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

import java.util.ArrayList;
import java.util.List;

public class BrowserConcurrencyTests {

    @Test
    public void testBrowserShouldBePossibleToHaveTwoBrowsersForTwoThreads() throws InterruptedException {

        Thread thread01 = new Thread(new BrowserThread(TheInternetPage.CHECKBOXES.getAddress()));
        Thread thread02 = new Thread(new BrowserThread(TheInternetPage.LOGIN.getAddress()));

        thread01.start();
        thread02.start();

        thread01.join();
        thread02.join();

        thread01.interrupt();
        thread02.interrupt();
    }

    @Test
    public void testShouldBePossibleToUseParallelStreams(){
        Browser browser = AqualityServices.getBrowser();
        browser.goTo(TheInternetPage.TABLES.getAddress());
        List<ILabel> textBoxes = new ElementFactory().findElements(By.xpath("//td"), ElementType.LABEL);
        List<String> texts = new ArrayList<>();
        textBoxes.parallelStream().forEach(lbl -> {
            // set the same instance of browser for all threads
            AqualityServices.setBrowser(browser);
            String text = lbl.getText(HighlightState.HIGHLIGHT);
            // processing results of work trough web driver (getting text)
            String updatedText = text  + "_updated";
            texts.add(text);
            texts.add(updatedText);
        });
        browser.quit();
        Assert.assertEquals(texts.size(), textBoxes.size()*2);
    }

    class BrowserThread implements Runnable
    {
        private final String url;
        BrowserThread(String url){
            this.url = url;
        }
        @Override
        public void run() {
            AqualityServices.getBrowser().goTo(url);
            Assert.assertEquals(url, AqualityServices.getBrowser().getCurrentUrl());
            AqualityServices.getBrowser().quit();
        }
    }
}
