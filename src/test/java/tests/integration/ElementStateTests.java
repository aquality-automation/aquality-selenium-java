package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.DynamicControlsForm;
import theinternet.forms.DynamicLoadingForm;
import utils.DurationSample;
import utils.Timer;

import java.time.Duration;

public class ElementStateTests extends BaseTest {

    private final Duration customWaitTime = Duration.ofSeconds(2L);
    private final double customDeviation = 2;
    private final double defaultDeviation = 7;
    private final IElementFactory elementFactory = AqualityServices.get(IElementFactory.class);
    private final ILabel lblNotExists = elementFactory.getLabel(By.xpath("//div[@class='not exist element']"), "not exist element");

    @Test
    public void testElementShouldWaitForEnabledWithCustomTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        Duration waitTime = customWaitTime;

        Timer timer = new Timer();
        timer.start();
        boolean isEnabled = new DynamicControlsForm().getTxbInput().state().waitForEnabled(waitTime);
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, customDeviation);

        Assert.assertFalse(isEnabled);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testElementShouldWaitForEnabledWithDefaultTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        long waitTime = AqualityServices.getConfiguration().getTimeoutConfiguration().getCondition().getSeconds();

        Timer timer = new Timer();
        timer.start();
        boolean isEnabled = new DynamicControlsForm().getTxbInput().state().waitForEnabled();
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, defaultDeviation);

        Assert.assertFalse(isEnabled);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testElementShouldWaitForNotEnabledWithCustomTimeout() {
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        Duration waitTime = customWaitTime;

        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        dynamicControlsForm.getBtnEnable().click();
        dynamicControlsForm.getTxbInput().state().waitForEnabled();

        Timer timer = new Timer();
        timer.start();
        boolean isDisabled = dynamicControlsForm.getTxbInput().state().waitForNotEnabled(waitTime);
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, defaultDeviation);

        Assert.assertFalse(isDisabled);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
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
        long waitTime = AqualityServices.getConfiguration().getTimeoutConfiguration().getCondition().getSeconds();

        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        dynamicControlsForm.getBtnEnable().click();
        dynamicControlsForm.getTxbInput().state().waitForEnabled();

        Timer timer = new Timer();
        timer.start();
        boolean isDisabled = dynamicControlsForm.getTxbInput().state().waitForNotEnabled();
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, defaultDeviation);

        Assert.assertFalse(isDisabled);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
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
        Duration waitTime = customWaitTime;
        dynamicControlsForm.getBtnRemove().click();

        Timer timer = new Timer();
        timer.start();
        boolean isMissed = dynamicControlsForm.getChbACheckbox().state().waitForNotExist(waitTime);
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, customDeviation);

        Assert.assertTrue(isMissed);
        Assert.assertTrue(durationSample.isDurationBetweenLimits(), durationSample.toString());
    }

    @Test
    public void testShouldBePossibleToWaitElementNotExists(){
        navigate(TheInternetPage.DYNAMIC_CONTROLS);
        DynamicControlsForm dynamicControlsForm = new DynamicControlsForm();
        long waitTime = AqualityServices.getConfiguration().getTimeoutConfiguration().getCondition().getSeconds();
        dynamicControlsForm.getBtnRemove().click();

        Timer timer = new Timer();
        timer.start();
        boolean isMissed = dynamicControlsForm.getChbACheckbox().state().waitForNotExist();
        DurationSample durationSample = new DurationSample(timer.duration(), waitTime, defaultDeviation);


        Assert.assertTrue(isMissed);
        Assert.assertTrue(durationSample.getDuration() < waitTime, durationSample.toString());
    }
}
