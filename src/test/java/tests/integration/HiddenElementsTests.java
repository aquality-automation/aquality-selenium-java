package tests.integration;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ExpectedCount;
import aquality.selenium.elements.interfaces.ILabel;
import automationpractice.forms.SliderForm;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.util.List;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class HiddenElementsTests extends BaseTest {

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        BrowserManager.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
    }

    @Test
    public void testHiddenElementExist() {
        Assert.assertTrue(new SliderForm().getBtnAddToCart(ElementState.EXISTS_IN_ANY_STATE).state().isExist());
    }

    @Test
    public void testHiddenElementsExist() {
        List<ILabel> listElements = new SliderForm().getListElements(ElementState.EXISTS_IN_ANY_STATE, ExpectedCount.MORE_THEN_ZERO);
        Assert.assertFalse(listElements.isEmpty());
        Assert.assertTrue(listElements.stream().allMatch(el -> el.state().waitForExist()));
    }

    @Test
    public void testNotHiddenElementsNotDisplayed() {
        List<ILabel> listElements = new SliderForm().getListElements(ElementState.DISPLAYED, ExpectedCount.MORE_THEN_ZERO);
        Assert.assertFalse(listElements.isEmpty());
        Assert.assertFalse(listElements.stream().anyMatch(label -> label.state().waitForDisplayed(1L)));
    }
}
