package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import automationpractice.forms.SliderForm;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.SiteLoader;

public class ElementExistNotDisplayedTest extends BaseTest {

    @BeforeMethod
    @Override
    public void beforeMethod() {
        SiteLoader.openAutomationPracticeSite();
    }

    @Test
    public void testElementExistNotDisplayed() {
        IButton button = new SliderForm().getBtnAddToCart(ElementState.EXISTS_IN_ANY_STATE);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> button.state().isExist() && !button.state().isDisplayed(), "Button should exists in the DOM but should not be displayed "));
    }
}
