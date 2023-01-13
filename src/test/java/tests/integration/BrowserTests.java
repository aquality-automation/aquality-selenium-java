package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.BrowserName;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicContentForm;
import theinternet.forms.FormAuthenticationForm;
import theinternet.forms.WelcomeForm;
import utils.DurationSample;
import utils.Timer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;

import static utils.FileUtil.getResourceFileByName;


public class BrowserTests extends BaseTest {

    private static final double defaultDeviation = 3;

    @Test
    public void testShouldBePossibleToStartBrowserAndNavigate(){
        getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(getBrowser().getCurrentUrl(), TheInternetPage.LOGIN.getAddress());
    }

    @Test
    public void testShouldBePossibleToGetPerformanceLogs(){
        getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertFalse(getBrowser().getLogs(LogType.PERFORMANCE).getAll().isEmpty(),
                "Some performance logs should be presented");
    }

    @Test
    public void testBrowserShouldKeepAndProvideWebDriver(){
        getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(getBrowser().getDriver().getCurrentUrl(), TheInternetPage.LOGIN.getAddress());
    }

    @Test
    public void testShouldBePossibleToNavigateBackAndForward() {
        String firstNavigationUrl = TheInternetPage.LOGIN.getAddress();
        String secondNavigationUrl = TheInternetPage.CHECKBOXES.getAddress();

        getBrowser().goTo(firstNavigationUrl);
        Assert.assertEquals(getBrowser().getCurrentUrl(), firstNavigationUrl);

        getBrowser().goTo(secondNavigationUrl);
        Assert.assertEquals(getBrowser().getCurrentUrl(), secondNavigationUrl);

        getBrowser().goBack();
        Assert.assertEquals(getBrowser().getCurrentUrl(), firstNavigationUrl);

        getBrowser().goForward();
        Assert.assertEquals(getBrowser().getCurrentUrl(), secondNavigationUrl);
    }

    @Test
    public void testShouldBePossibleToOpenNewBrowserAfterQuit(){
        String url = new FormAuthenticationForm().getUrl();
        getBrowser().goTo(url);
        getBrowser().quit();

        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertNotEquals(currentUrl.toLowerCase(), url.toLowerCase());
    }

    @Test
    public void testBrowserShouldBePossibleToRefreshPage(){
        DynamicContentForm dynamicContentForm = new DynamicContentForm();
        getBrowser().goTo(dynamicContentForm.getUrl());
        String firstItemText = dynamicContentForm.getFirstContentItem().getText();

        getBrowser().refresh();
        getBrowser().waitForPageToLoad();
        String firstItemTextAfterRefresh = dynamicContentForm.getFirstContentItem().getText();
        Assert.assertNotEquals(firstItemText, firstItemTextAfterRefresh);
    }

    @Test(expectedExceptions = TimeoutException.class)
    public void testShouldBePossibleToSetPageLoadTimeout(){
        getBrowser().setPageLoadTimeout(Duration.ofMillis(300));
        String urlAquality = "https://github.com/aquality-automation";
        getBrowser().goTo(urlAquality);
    }

    @Test
    public void testShouldBePossibleToMakeScreenshot() {
        getBrowser().goTo(new FormAuthenticationForm().getUrl());
        getBrowser().waitForPageToLoad();

        Assert.assertTrue(getBrowser().getScreenshot().length > 0);
    }

    @Test
    public void testShouldBePossibleToExecuteJavaScript(){
        String url = new DynamicContentForm().getUrl();
        getBrowser().goTo(url);
        getBrowser().waitForPageToLoad();

        String currentUrl = String.valueOf(getBrowser().executeScript("return window.location.href"));
        Assert.assertEquals(currentUrl, url);
    }

    @Test
    public void testShouldBePossibleToExecuteJavaScriptAsync(){
        String url = new DynamicContentForm().getUrl();
        getBrowser().goTo(url);
        getBrowser().waitForPageToLoad();

        long expectedDurationInSeconds = 1;

        Timer timer = new Timer();
        timer.start();
        getBrowser().executeAsyncScript(getAsyncTimeoutJavaScript(expectedDurationInSeconds));

        DurationSample durationSample = new DurationSample(timer.duration(), expectedDurationInSeconds, defaultDeviation);

        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test (expectedExceptions = ScriptTimeoutException.class)
    public void testScriptTimeoutExeceptionShouldBeThrownIfScriptTimeoutIsOver(){
        String url = new DynamicContentForm().getUrl();
        getBrowser().goTo(url);
        getBrowser().waitForPageToLoad();

        long expectedDurationInSeconds = AqualityServices.getConfiguration().getTimeoutConfiguration().getScript().getSeconds() + 1;
        getBrowser().executeAsyncScript(getAsyncTimeoutJavaScript(expectedDurationInSeconds));
    }

    @Test
    public void testShouldBePossibleToExecuteJavaScriptFile() throws IOException {
        String url = new DynamicContentForm().getUrl();
        getBrowser().goTo(url);
        getBrowser().waitForPageToLoad();

        String currentUrl = String.valueOf(getBrowser().executeScript(getResourceFileByName("js_current_url.js")));
        Assert.assertEquals(currentUrl, url);
    }

    @Test
    public void testShouldBePossibleToExecuteJavaScriptPredefinedFile() {
        FormAuthenticationForm authenticationForm = new FormAuthenticationForm();
        getBrowser().goTo(authenticationForm.getUrl());
        getBrowser().waitForPageToLoad();

        String valueToSet = "username";
        getBrowser().executeScript(JavaScript.SET_VALUE, authenticationForm.getTxbUsername().getElement(), valueToSet);
        Assert.assertEquals(authenticationForm.getTxbUsername().getValue(), valueToSet);
    }

    @Test
    public void testShouldBePossibleToSetWindowSize() {
        Dimension initialSize = getBrowser().getDriver().manage().window().getSize();
        Dimension testedSize = new Dimension(600, 600);

        getBrowser().setWindowSize(testedSize.width, testedSize.height);
        Assert.assertTrue(getBrowser().getDriver().manage().window().getSize().height < initialSize.height);
        Assert.assertTrue(getBrowser().getDriver().manage().window().getSize().width < initialSize.width);
        Assert.assertTrue(getBrowser().getDriver().manage().window().getSize().width >= testedSize.width);

        getBrowser().maximize();
        Assert.assertNotEquals(getBrowser().getDriver().manage().window().getSize(), testedSize);
        Assert.assertTrue(getBrowser().getDriver().manage().window().getSize().height > testedSize.height);
        Assert.assertTrue(getBrowser().getDriver().manage().window().getSize().width > testedSize.width);

        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
        Assert.assertEquals(getBrowser().getDriver().manage().window().getSize(), defaultSize);
    }

    @Test
    public void testShouldBePossibleToScrollWindowBy(){
        WelcomeForm scrollForm = new WelcomeForm();
        getBrowser().goTo(scrollForm.getUrl());
        int initialY = scrollForm.getFormPointInViewPort().getY();
        int formHeight = scrollForm.getSize().getHeight();
        getBrowser().scrollWindowBy(0, formHeight);
        Assert.assertEquals(initialY - scrollForm.getFormPointInViewPort().getY(), formHeight);
    }

    @Test
    public void testShouldBePossibleToGetBrowserName() {
        BrowserName expectedBrowserName = BrowserName.valueOf(getSettings().getValue("/browserName").toString().toUpperCase());
        Assert.assertEquals(getBrowser().getBrowserName(), expectedBrowserName);
    }

    @Test
    public void testShouldBePossibleToSetImplicitWait(){
        long waitTime = 5L;
        getBrowser().setImplicitWaitTimeout(Duration.ofSeconds(waitTime));

        Timer timer = new Timer();
        timer.start();
        DurationSample durationSample = null;
        try{
            getBrowser().getDriver().findElement(By.id("not_exist_element"));
        }catch (NoSuchElementException e){
            durationSample = new DurationSample(timer.duration(), waitTime, defaultDeviation);
        }
        Assert.assertNotNull(durationSample);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testShouldBePossibleToGetDownloadDir(){
        Assert.assertFalse(getBrowser().getDownloadDirectory().isEmpty(), "Browser download directory should not be empty " + getBrowser().getDownloadDirectory());
    }

    private ISettingsFile getSettings() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        try {
            return new JsonSettingsFile(getResourceFileByName(settingsProfile));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String getAsyncTimeoutJavaScript(double expectedDurationInSeconds){
        return "window.setTimeout(arguments[arguments.length - 1], " + expectedDurationInSeconds*1000 + ");";
    }
}