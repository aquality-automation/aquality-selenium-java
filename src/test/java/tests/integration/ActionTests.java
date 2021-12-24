package tests.integration;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.actions.JsActions;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import automationpractice.forms.ProductForm;
import automationpractice.forms.ProductListForm;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;
import utils.SiteLoader;

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class ActionTests extends BaseTest {

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        AqualityServices.getBrowser().getDriver().manage().window().maximize();
        if (!AqualityServices.getBrowser().getCurrentUrl().equals(URL_AUTOMATIONPRACTICE)) {
            SiteLoader.openAutomationPracticeSite();
        }
    }

    @Test
    public void testScrollToTheCenter() {
        JsActions jsActions = new ProductListForm().getLblLastProduct().getJsActions();
        jsActions.scrollToTheCenter();
        Assert.assertTrue(jsActions.isElementOnScreen(), "element is not on the screen after scrollToTheCenter()");
    }

    @Test
    public void testScrollIntoView() {
        AqualityServices.getBrowser().executeScript(JavaScript.SCROLL_TO_BOTTOM);
        JsActions jsActions = new ProductListForm().getLblLastProduct().getJsActions();
        jsActions.scrollIntoView();
        Assert.assertTrue(jsActions.isElementOnScreen(), "element is not on the screen after scrollIntoView()");
    }

    @Test
    public void testMoveMouseToElement() {
        IButton button = new ProductListForm().getBtnLastProductMoreFocused();
        Assert.assertTrue(button.getText().contains("More"), "element is not focused after moveMouseToElement()");
    }

    @Test
    public void testMoveMouseFromElement() {
        ProductListForm productListForm = new ProductListForm();

        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> {
            IButton button = productListForm.getBtnLastProductMoreFocused();
            return button.getText().contains("More");
        }, "element is not focused after moveMouseToElement()"));

        IButton button = productListForm.getBtnLastProductMoreFocused();
        productListForm.getLblLastProduct().getMouseActions().moveMouseFromElement();
        Assert.assertFalse(button.state().isDisplayed(), "element is still focused after moveMouseFromElement()");
    }

    @Test
    public void testGetElementText() {
        IButton button = new ProductListForm().getBtnLastProductMoreFocused();
        Assert.assertEquals(button.getText().trim(), button.getJsActions().getElementText().trim(),
                "element text got via JsActions is not match to expected");
    }

    @Test
    public void testSetFocus() {
        new ProductListForm().getBtnLastProductMoreFocused().getJsActions().clickAndWait();

        ITextBox txbQuantity = new ProductForm().getTxbQuantity();
        txbQuantity.getJsActions().setFocus();
        txbQuantity.sendKeys(Keys.DELETE);
        txbQuantity.sendKeys(Keys.BACK_SPACE);
        Assert.assertEquals(txbQuantity.getValue(), "",
                "value is not empty after sending Delete keys, probably setFocus() didn't worked");
    }

    @Test
    public void testSetValue() {
        new ProductListForm().getBtnLastProductMoreFocused().getJsActions().clickAndWait();

        ProductForm productForm = new ProductForm();
        ITextBox txbQuantity = productForm.getTxbQuantity();
        txbQuantity.getJsActions().setValue("2");
        productForm.getBtnPlus().click();
        Assert.assertEquals(txbQuantity.getValue(), "3",
                "value of textbox is not correct, probably setValue() didn't worked");
    }
}
