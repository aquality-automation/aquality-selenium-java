package tests.integration;

import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.ElementFactory;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicControlsForm;
import theinternet.forms.DynamicLoadingForm;

public class ElementStateTests extends BaseTest {

    private final double operationTime = 2;
    private final long customWaitTime = 2L;
    private final ElementFactory elementFactory = new ElementFactory();
    private final ILabel lblNotExists = elementFactory.getLabel(By.xpath("//div[@class='not exist element']"), "not exist element");

    @Test
    public void testElementShouldWaitForEnabledWithCustomTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        long waitTime = customWaitTime;

        long startTime = System.nanoTime();
        boolean isEnabled = new DynamicControlsForm().getTxbInput().state().waitForEnabled(waitTime);
        double duration = calculateDuration(startTime);

        Assert.assertFalse(isEnabled);
        Assert.assertTrue(duration >= waitTime && duration <= (waitTime + operationTime));
    }

    @Test
    public void testElementShouldWaitForEnabledWithDefaultTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        long waitTime = Configuration.getInstance().getTimeoutConfiguration().getCondition();

        long startTime = System.nanoTime();
        boolean isEnabled = new DynamicControlsForm().getTxbInput().state().waitForEnabled();
        double duration = calculateDuration(startTime);

        Assert.assertFalse(isEnabled);
        Assert.assertTrue(duration >= waitTime && duration <= (waitTime + operationTime));
    }

    @Test
    public void testElementShouldWaitForNotEnabledWithCustomTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        long waitTime = customWaitTime;

        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        dynamicControlsForm.getBtnEnable().click();
        dynamicControlsForm.getTxbInput().state().waitForEnabled();

        long startTime = System.nanoTime();
        boolean isDisabled = dynamicControlsForm.getTxbInput().state().waitForNotEnabled(waitTime);
        double duration = calculateDuration(startTime);

        Assert.assertFalse(isDisabled);
        Assert.assertTrue(duration >= waitTime && duration <= (waitTime + operationTime));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNoSuchShouldBeThrownForWaitEnabledIfElementNotFound(){
        lblNotExists.state().waitForEnabled(customWaitTime);
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testNoSuchShouldBeThrownForWaitNotEnabledIfElementNotFound(){
        lblNotExists.state().waitForNotEnabled(customWaitTime);
    }

    @Test
    public void testElementShouldWaitForNotEnabledWithDefaultTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        long waitTime = Configuration.getInstance().getTimeoutConfiguration().getCondition();

        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        dynamicControlsForm.getBtnEnable().click();
        dynamicControlsForm.getTxbInput().state().waitForEnabled();

        long startTime = System.nanoTime();
        boolean isDisabled = dynamicControlsForm.getTxbInput().state().waitForNotEnabled();
        double duration = calculateDuration(startTime);

        Assert.assertFalse(isDisabled);
        Assert.assertTrue(duration >= waitTime && duration <= (waitTime + operationTime));
    }

    private double calculateDuration(long startTimeNanoSec){
        return (System.nanoTime() - startTimeNanoSec)/Math.pow(10,9);
    }

    @Test
    public void testTextBoxEnabled() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        dynamicControlsForm.getBtnEnable().click();
        dynamicControlsForm.getTxbInput().state().waitForEnabled();
        Assert.assertTrue(dynamicControlsForm.getTxbInput().state().isEnabled());
    }

    @Test
    public void testWaitInvisibility() {
        navigate(TheInternetPage.DYNAMIC_LOADING);
        DynamicLoadingForm loadingForm = new DynamicLoadingForm();

        loadingForm.getBtnStart().waitAndClick();

        ILabel lblLoading = loadingForm.getLblLoading();
        String id = lblLoading.getAttribute("id", HighlightState.HIGHLIGHT);
        String id2 = lblLoading.getAttribute("id");
        String loadingText = "loading";

        Assert.assertEquals(id,loadingText);
        Assert.assertEquals(id2,loadingText);

        lblLoading.state().waitForDisplayed(2L);

        boolean status = lblLoading.state().waitForNotDisplayed(2L);
        Assert.assertFalse(status);

        status = lblLoading.state().waitForNotDisplayed();
        Assert.assertTrue(status);

        String finishText = loadingForm.getLblFinish().getText(HighlightState.HIGHLIGHT);

        Assert.assertTrue(finishText.contains("Hello World!"));
    }

    @Test
    public void testWaitForExist(){
        navigate(TheInternetPage.DYNAMIC_LOADING);
        DynamicLoadingForm loadingForm = new DynamicLoadingForm();
        boolean status = loadingForm.getLblLoading().state().waitForExist(loadingForm.getTimeout());

        Assert.assertFalse(status);
        Assert.assertTrue(loadingForm.getBtnStart().state().waitForExist());
    }

    @Test
    public void testShouldBePossibleToWaitElementNotExistsCustom(){
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        long waitTime = customWaitTime;
        dynamicControlsForm.getBtnRemove().click();
        long startTime = System.nanoTime();
        boolean isMissed = dynamicControlsForm.getChbACheckbox().state().waitForNotExist(waitTime);
        double duration = calculateDuration(startTime);

        Assert.assertFalse(isMissed);
        Assert.assertTrue(duration >= waitTime && duration <= (waitTime + operationTime));
    }

    @Test
    public void testShouldBePossibleToWaitElementNotExists(){
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        long waitTime = Configuration.getInstance().getTimeoutConfiguration().getCondition();
        dynamicControlsForm.getBtnRemove().click();

        long startTime = System.nanoTime();
        boolean isMissed = dynamicControlsForm.getChbACheckbox().state().waitForNotExist();
        double duration = calculateDuration(startTime);

        Assert.assertTrue(isMissed);
        Assert.assertTrue(duration < waitTime);
    }
}
