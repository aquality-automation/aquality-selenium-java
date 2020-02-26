package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import automationpractice.forms.SliderForm;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class ElementExistNotDisplayedTest extends BaseTest {

    @BeforeMethod
    @Override
    public void beforeMethod() {
        AqualityServices.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
    }

    @Test
    public void testElementExistNotDisplayed() {
        IButton button = new SliderForm().getBtnAddToCart(ElementState.EXISTS_IN_ANY_STATE);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> button.state().isExist() && !button.state().isDisplayed(), "Button should exists in the DOM but should not be displayed "));
    }
}
