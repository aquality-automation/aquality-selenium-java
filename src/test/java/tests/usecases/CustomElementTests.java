package tests.usecases;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.TextBox;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;
import theinternet.TheInternetPage;
import theinternet.forms.FormAuthenticationForm;

public class CustomElementTests extends BaseTest {

    @Test
    public void testCustomTextBox() {
        AqualityServices.getBrowser().goTo(TheInternetPage.LOGIN.getAddress());
        FormAuthenticationForm authenticationForm = new FormAuthenticationForm();
        ITextBox txbUsername = authenticationForm.getTxbUsername();

        CustomTextBox customTxbUsername = AqualityServices.getElementFactory()
                .getCustomElement(CustomTextBox::new, txbUsername.getLocator(), txbUsername.getName(), ElementState.EXISTS_IN_ANY_STATE);
        txbUsername.type("wrong");
        customTxbUsername.type("right");
        Assert.assertEquals(txbUsername.getValue(), customTxbUsername.getText(),
                "custom textbox's value is incorrect");
    }

    private class CustomTextBox extends TextBox {

        CustomTextBox(By locator, String name, ElementState state) {
            super(locator, name, state);
        }

        @Override
        public String getText() {
            return getValue();
        }
    }

}