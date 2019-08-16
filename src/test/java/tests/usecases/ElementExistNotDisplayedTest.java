package tests.usecases;

import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import utils.ConditionalWait;
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
        BrowserManager.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
    }

    @Test
    public void testElementExistNotDisplayed() {
        IButton button = new SliderForm().getBtnAddToCart(ElementState.EXISTS_IN_ANY_STATE);
        Assert.assertTrue(ConditionalWait.waitFor(driver -> button.state().isExist() && !button.state().isDisplayed()));
    }
}
