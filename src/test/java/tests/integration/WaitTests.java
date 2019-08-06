package tests.integration;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.ILabel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicLoadingForm;

public class WaitTests extends BaseTest {

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        BrowserManager.getBrowser().goTo(TheInternetPage.DYNAMIC_LOADING.getAddress());
    }

    @Test
    public void testWaitInvisibility() {
        DynamicLoadingForm loadingForm = new DynamicLoadingForm();

        loadingForm.getBtnStart().waitAndClick();

        ILabel lblLoading = loadingForm.getLblLoading();
        String id = lblLoading.getAttribute("id", HighlightState.HIGHLIGHT);
        String id2 = lblLoading.getAttribute("id", HighlightState.HIGHLIGHT, loadingForm.getTimeout());
        String id3 = lblLoading.getAttribute("id", loadingForm.getTimeout());
        String loadingText = "loading";
        Assert.assertEquals(id,loadingText);
        Assert.assertEquals(id2,loadingText);
        Assert.assertEquals(id3,loadingText);

        lblLoading.waitForDisplayed(2L);

        boolean status = lblLoading.waitForNotDisplayed(2L);
        Assert.assertFalse(status);

        status = lblLoading.waitForNotDisplayed();
        Assert.assertTrue(status);

        String finishText = loadingForm.getLblFinish().getText(HighlightState.HIGHLIGHT);

        Assert.assertTrue(finishText.contains("Hello World!"));
    }

    @Test
    public void testWaitForExist(){
        DynamicLoadingForm loadingForm = new DynamicLoadingForm();

        boolean status = loadingForm.getLblLoading().waitForExist(loadingForm.getTimeout());
        Assert.assertFalse(status);

        Assert.assertTrue(loadingForm.getBtnStart().waitForExist());
    }

    @Test
    public void testWaitDoesNotExists(){
        DynamicLoadingForm loadingForm = new DynamicLoadingForm();

        Assert.assertFalse(loadingForm.getBtnStart().waitForNotExist(loadingForm.getTimeout()));

        Assert.assertTrue(loadingForm.getLblLoading().waitForNotExist());
    }
}
