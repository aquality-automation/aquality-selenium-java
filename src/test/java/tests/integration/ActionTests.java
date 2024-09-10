package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.FormAuthenticationForm;
import theinternet.forms.InfiniteScrollForm;
import theinternet.forms.JQueryMenuForm;
import theinternet.forms.WelcomeForm;

import java.awt.*;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class ActionTests extends BaseTest {

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        getBrowser().maximize();
    }

    @Test
    public void testScrollToTheCenter() {
        final int accuracy = 1;
        WelcomeForm welcomeForm = new WelcomeForm();
        getBrowser().goTo(welcomeForm.getUrl());
        ILink link = welcomeForm.getExampleLink(WelcomeForm.AvailableExample.HOVERS);
        link.getJsActions().scrollToTheCenter();

        Long windowHeight = getScriptResultOrDefault("getWindowSize.js", 10L);
        double currentY = getScriptResultOrDefault("getElementYCoordinate.js", 0.0, link.getElement());
        double coordinateRelatingWindowCenter = windowHeight.doubleValue() / 2 - currentY;
        Assert.assertTrue(Math.abs(coordinateRelatingWindowCenter) <= accuracy,
                "Upper bound of element should be in the center of the page");
    }

    @Test
    public void testScrollToElement() throws TimeoutException {
        InfiniteScrollForm infiniteScrollForm = new InfiniteScrollForm();
        getBrowser().goTo(infiniteScrollForm.getUrl());
        infiniteScrollForm.waitForMoreExamples();
        Dimension size = infiniteScrollForm.getLastExampleLabel().visual().getSize();
        getBrowser().scrollWindowBy(size.width, size.height);
        getBrowser().setWindowSize(size.width, size.height);
        getBrowser().scrollWindowBy(0, 0);
        int defaultCount = infiniteScrollForm.getExampleLabels().size();
        AtomicReference<ILabel> lastExampleLabel = new AtomicReference<>(infiniteScrollForm.getLastExampleLabel());
        AqualityServices.getConditionalWait().waitForTrue(() -> {
            lastExampleLabel.set(infiniteScrollForm.getLastExampleLabel());
            lastExampleLabel.get().getMouseActions().scrollToElement();
            return infiniteScrollForm.getExampleLabels().size() > defaultCount;
        }, "Some examples should be added after scroll");
    }

    @Test
    public void testScrollFromOrigin() throws TimeoutException {
        InfiniteScrollForm infiniteScrollForm = new InfiniteScrollForm();
        getBrowser().goTo(infiniteScrollForm.getUrl());
        int defaultCount = infiniteScrollForm.getExampleLabels().size();
        AtomicReference<ILabel> lastExampleLabel = new AtomicReference<>(infiniteScrollForm.getLastExampleLabel());
        AqualityServices.getConditionalWait().waitForTrue(() -> {
            lastExampleLabel.set(infiniteScrollForm.getLastExampleLabel());
            lastExampleLabel.get().getMouseActions().scrollFromOrigin(0, lastExampleLabel.get().visual().getSize().height);
            return infiniteScrollForm.getExampleLabels().size() > defaultCount;
        }, "Some examples should be added after scroll");
    }

    @Test
    public void testScrollIntoView() throws TimeoutException {
        InfiniteScrollForm infiniteScrollForm = new InfiniteScrollForm();
        getBrowser().goTo(infiniteScrollForm.getUrl());
        infiniteScrollForm.waitForMoreExamples();
        int defaultCount = infiniteScrollForm.getExampleLabels().size();
        AtomicReference<ILabel> lastExampleLabel = new AtomicReference<>(infiniteScrollForm.getLastExampleLabel());
        AqualityServices.getConditionalWait().waitForTrue(() -> {
            lastExampleLabel.set(infiniteScrollForm.getLastExampleLabel());
            lastExampleLabel.get().getJsActions().scrollIntoView();
            return infiniteScrollForm.getExampleLabels().size() > defaultCount;
        }, "Some examples should be added after scroll");
        Assert.assertTrue(lastExampleLabel.get().getJsActions().isElementOnScreen(),
                "Element should be on screen after scroll into view");
    }

    @Test
    public void testMoveMouseToElement() {
        JQueryMenuForm menuForm = new JQueryMenuForm();
        getBrowser().goTo(menuForm.getUrl());
        menuForm.getEnabledButton().getMouseActions().moveMouseToElement();
        Assert.assertTrue(menuForm.isEnabledButtonFocused(), "element is not focused after moveMouseToElement()");
    }

    @Test
    public void testMoveMouseFromElement() {
        JQueryMenuForm menuForm = new JQueryMenuForm();
        testMoveMouseToElement();
        menuForm.getEnabledButton().getMouseActions().moveMouseFromElement();
        boolean isUnfocused = AqualityServices.getConditionalWait().waitFor(() -> !menuForm.isEnabledButtonFocused());
        Assert.assertTrue(isUnfocused, "element is still focused after moveMouseFromElement()");
    }

    @Test
    public void testGetElementText() {
        WelcomeForm welcomeForm = new WelcomeForm();
        getBrowser().goTo(welcomeForm.getUrl());
        ILink link = welcomeForm.getExampleLink(WelcomeForm.AvailableExample.HOVERS);
        Assert.assertEquals(link.getText().trim(), link.getJsActions().getElementText().trim(),
                "element text got via JsActions is not match to expected");
    }

    @Test
    public void testSetFocus() {
        FormAuthenticationForm form = new FormAuthenticationForm();
        getBrowser().goTo(form.getUrl());
        ITextBox textBox = form.getTxbUsername();
        ITextBox secondTextBox = form.getTxbPassword();
        textBox.clearAndType("peter.parker@example.com");
        secondTextBox.getJsActions().setFocus();

        String currentText = textBox.getValue();
        String expectedText = currentText.substring(0, currentText.length() - 1);
        textBox.getJsActions().setFocus();
        textBox.sendKeys(Keys.DELETE);
        textBox.sendKeys(Keys.BACK_SPACE);
        Assert.assertEquals(textBox.getValue(), expectedText, "One character should be removed from " + expectedText);
    }

    @Test
    public void testClear() {
        FormAuthenticationForm form = new FormAuthenticationForm();
        getBrowser().goTo(form.getUrl());
        ITextBox textBox = form.getTxbUsername();
        textBox.clear();
        Assert.assertTrue(StringUtils.isEmpty(textBox.getValue()), "Clear should work when was initially empty");
        textBox.type("anything");
        textBox.clear();
        Assert.assertTrue(StringUtils.isEmpty(textBox.getValue()), "Clear should work when was not empty");
        textBox.type("anything");
        textBox.clear();
        textBox.clear();
        Assert.assertTrue(StringUtils.isEmpty(textBox.getValue()), "Clear should work called twice");
    }

    @Test
    public void testSetValue() {
        final String expectedValue = "2";
        FormAuthenticationForm form = new FormAuthenticationForm();
        getBrowser().goTo(form.getUrl());
        ITextBox textBox = form.getTxbUsername();
        textBox.getJsActions().setValue(expectedValue);
        Assert.assertEquals(textBox.getValue(), expectedValue, "Text is not set to value");
    }
}
