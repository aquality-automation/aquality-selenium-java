package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import theinternet.TheInternetPage;

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

    class BrowserThread implements Runnable
    {
        private final String url;
        BrowserThread(String url){
            this.url = url;
        }
        @Override
        public void run() {
            BrowserManager.getBrowser().goTo(url);
            Assert.assertEquals(url, BrowserManager.getBrowser().getCurrentUrl());
            BrowserManager.getBrowser().quit();
        }
    }
}
