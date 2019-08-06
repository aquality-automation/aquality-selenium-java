package tests.integration;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.logger.Logger;
import automationpractice.forms.SliderForm;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import theinternet.TheInternetPage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;


public class BrowserTests extends BaseTest {

    @Test
    public void testBrowserShouldBeOpenedAfterQuitAndSecondStart(){
        BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        BrowserManager.getBrowser().quit();
        BrowserManager.getBrowser().goTo(TheInternetPage.CHECKBOXES.getAddress());
        BrowserManager.getBrowser().quit();
    }

    @Test
    public void testWindowSize() {
        Dimension initialSize = BrowserManager.getBrowser().getDriver().manage().window().getSize();
        Dimension testedSize = new Dimension(600, 600);

        BrowserManager.getBrowser().setSize(testedSize.width, testedSize.height);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().height < initialSize.height);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().height >= testedSize.height);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().width < initialSize.width);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().width >= testedSize.width);

        BrowserManager.getBrowser().maximize();
        Assert.assertNotEquals(BrowserManager.getBrowser().getDriver().manage().window().getSize(), testedSize);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().height > testedSize.height);
        Assert.assertTrue(BrowserManager.getBrowser().getDriver().manage().window().getSize().width > testedSize.width);

        BrowserManager.getBrowser().setSize(defaultSize.width, defaultSize.height);
        Assert.assertEquals(BrowserManager.getBrowser().getDriver().manage().window().getSize(), defaultSize);
    }

    @Test
    public void testPageNavigation() {
        BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.LOGIN.getAddress());

        BrowserManager.getBrowser().goTo(TheInternetPage.CHECKBOXES.getAddress());
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.CHECKBOXES.getAddress());

        BrowserManager.getBrowser().goBack();
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.LOGIN.getAddress());

        BrowserManager.getBrowser().goForward();
        Assert.assertEquals(BrowserManager.getBrowser().getCurrentUrl(), TheInternetPage.CHECKBOXES.getAddress());
    }

    @Test
    public void testScrollWindowBy(){
        BrowserManager.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
        SliderForm sliderForm = new SliderForm();
        int initialY = sliderForm.getFormPointInViewPort().getY();
        int formHeight = sliderForm.getFormSize().getHeight();
        BrowserManager.getBrowser().scrollWindowBy(0, formHeight);
        Assert.assertEquals(initialY - sliderForm.getFormPointInViewPort().getY(), formHeight);
    }

    @Test
    public void testScreenshot() {
        String baseDir = System.getProperty("user.dir") != null ? System.getProperty("user.dir") : System.getProperty("project.basedir");
        BrowserManager.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        SoftAssert softAssert = new SoftAssert();
        BrowserManager.getBrowser().waitForPageToLoad();
        String screensSubPath = "/src/test/screen/";
        File screensDir = Paths.get(baseDir, screensSubPath).toFile();
        if(!screensDir.mkdir()){
            Logger.getInstance().warn("failed to create screens dir");
        }
        File screenshotFile = Paths.get(screensDir.getPath(), "screen1.png").toFile();
        try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
            fos.write(BrowserManager.getBrowser().getScreenshot());
        } catch (IOException e) {
            Logger.getInstance().fatal(e.getMessage(), e);
        }
        softAssert.assertTrue(screenshotFile.exists(), "screenshot doesn't exist at ".concat(screenshotFile.getAbsolutePath()));
        delete(screenshotFile);
        delete(screensDir);
        softAssert.assertFalse(screenshotFile.exists(), "screenshot still exist after deletion at ".concat(screenshotFile.getPath()));
        softAssert.assertAll();
    }

    private void delete(File file){
        try{
            Files.delete(file.toPath());
        } catch (IOException e) {
            Logger.getInstance().warn(e.getMessage());
        }
    }
}