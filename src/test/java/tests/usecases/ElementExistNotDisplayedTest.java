package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.ILabel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.forms.HoversForm;

public class ElementExistNotDisplayedTest extends BaseTest {
    private final HoversForm hoversForm = new HoversForm();

    @BeforeMethod
    @Override
    public void beforeMethod() {
        getBrowser().goTo(hoversForm.getUrl());
    }

    @Test
    public void testElementExistNotDisplayed() {
        ILabel label = hoversForm.getHiddenElement(HoversForm.HoverExample.FIRST, ElementState.EXISTS_IN_ANY_STATE);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> label.state().isExist() && !label.state().isDisplayed(),
                "Label should exists in the DOM but should not be displayed"));
    }
}
