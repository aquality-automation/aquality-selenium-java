package tests.integration;

import aquality.selenium.browser.BrowserName;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.utils.JsonFile;
import automationpractice.forms.SliderForm;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicContentForm;
import theinternet.forms.FormAuthenticationForm;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;
import static utils.FileUtil.getResourceFileByName;
import static utils.TimeUtil.getCurrentTimeInSeconds;


public class BrowserTests extends BaseTest {

    @Test
    public void testShouldBePossibleToStartBrowserAndNavigate(){
        getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(getBrowser().getCurrentUrl(), TheInternetPage.LOGIN.getAddress());
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
        getBrowser().setPageLoadTimeout(1L);
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
        getBrowser().goTo(URL_AUTOMATIONPRACTICE);
        SliderForm sliderForm = new SliderForm();
        int initialY = sliderForm.getFormPointInViewPort().getY();
        int formHeight = sliderForm.getFormSize().getHeight();
        getBrowser().scrollWindowBy(0, formHeight);
        Assert.assertEquals(initialY - sliderForm.getFormPointInViewPort().getY(), formHeight);
    }

    @Test
    public void testShouldBePossibleToGetBrowserName() {
        BrowserName expectedBrowserName = BrowserName.valueOf(getSettings().getValue("/browserName").toString().toUpperCase());
        Assert.assertEquals(getBrowser().getBrowserName(), expectedBrowserName);
    }

    @Test
    public void testShouldBePossibleToSetImplicitWait(){
        long waitTime = 5L;
        long operationTime = 2L;
        getBrowser().setImplicitWaitTimeout(waitTime);

        double startTime = getCurrentTimeInSeconds();
        double endTime = 0L;
        try{
            getBrowser().getDriver().findElement(By.id("not_exist_element"));
        }catch (NoSuchElementException e){
            endTime = getCurrentTimeInSeconds();
        }
        Assert.assertTrue((endTime - startTime) < waitTime + operationTime);
        Assert.assertTrue((endTime - startTime) >= waitTime);
    }

    @Test
    public void testShouldBePossibleToGetDownloadDir(){
        List<String> listOfDownloadDirs = new ArrayList<>();
        listOfDownloadDirs.add("//home//selenium//downloads");
        listOfDownloadDirs.add("/Users/username/Downloads");
        listOfDownloadDirs.add("target//downloads");
        listOfDownloadDirs.add("/home/circleci/repo/target/downloads");

        boolean isDirFound = listOfDownloadDirs.stream()
                .anyMatch(dir -> getBrowser().getDownloadDirectory().toLowerCase().contains(dir.toLowerCase()));
        Assert.assertTrue(isDirFound, "Browser download directory is not correct " + getBrowser().getDownloadDirectory());
    }

    private JsonFile getSettings() {
        String settingsProfile = System.getProperty("profile") == null ? "settings.json" : "settings." + System.getProperty("profile") + ".json";
        try {
            return new JsonFile(getResourceFileByName(settingsProfile));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}