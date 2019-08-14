package tests.integration;

import aquality.selenium.browser.BrowserManager;
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

import static automationpractice.Constants.URL_AUTOMATIONPRACTICE;

public class ActionTests extends BaseTest {

    @BeforeMethod
    @Override
    protected void beforeMethod() {
        BrowserManager.getBrowser().getDriver().manage().window().maximize();
        if (!BrowserManager.getBrowser().getCurrentUrl().equals(URL_AUTOMATIONPRACTICE)) {
            BrowserManager.getBrowser().goTo(URL_AUTOMATIONPRACTICE);
        }
    }

    @Test
    public void testScrollToTheCenter() {
        JsActions jsActions = new ProductListForm().getLblLastProduct().getJsActions();
        jsActions.scrollToTheCenter();
        Assert.assertTrue(jsActions.isElementOnScreen(), "element is not on the screen after scrollToTheCenter()");
    }

    @Test
    public void testScrollToView() {
        BrowserManager.getBrowser().executeScript(JavaScript.SCROLL_TO_BOTTOM);
        JsActions jsActions = new ProductListForm().getLblLastProduct().getJsActions();
        jsActions.scrollToView();
        Assert.assertTrue(jsActions.isElementOnScreen(), "element is not on the screen after scrollToView()");
    }

    @Test
    public void testMoveMouseToElement() {
        ProductListForm productListForm = new ProductListForm();
        productListForm.getLblFirstProduct().getMouseActions().moveMouseToElement();
        productListForm.getLblLastProduct().getMouseActions().moveMouseToElement();
        IButton button = productListForm.getBtnLastProductMore();
        Assert.assertTrue(button.getText().contains("More"), "element is not focused after moveMouseToElement()");
    }

    @Test
    public void testMoveMouseFromElement() {
        ProductListForm productListForm = new ProductListForm();
        productListForm.getLblLastProduct().getMouseActions().moveMouseToElement();
        IButton button = productListForm.getBtnLastProductMore();
        Assert.assertTrue(button.getText().contains("More"), "element is not focused after moveMouseToElement()");
        productListForm.getLblLastProduct().getMouseActions().moveMouseFromElement();
        Assert.assertFalse(button.state().isDisplayed(), "element is still focused after moveMouseFromElement()");
    }

    @Test
    public void testGetElementText() {
        ProductListForm productListForm = new ProductListForm();
        productListForm.getLblFirstProduct().getMouseActions().moveMouseToElement();
        productListForm.getLblLastProduct().getMouseActions().moveMouseToElement();
        IButton button = productListForm.getBtnLastProductMore();
        Assert.assertEquals(button.getText().trim(), button.getJsActions().getElementText().trim(),
                "element text got via JsActions is not match to expected");
    }

    @Test
    public void testSetFocus() {
        ProductListForm productListForm = new ProductListForm();
        productListForm.getLblFirstProduct().getMouseActions().moveMouseToElement();
        productListForm.getLblLastProduct().getMouseActions().moveMouseToElement();
        productListForm.getBtnLastProductMore().getJsActions().clickAndWait();

        ITextBox txbQuantity = new ProductForm().getTxbQuantity();
        txbQuantity.getJsActions().setFocus();
        txbQuantity.sendKeys(Keys.DELETE);
        txbQuantity.sendKeys(Keys.BACK_SPACE);
        Assert.assertEquals(txbQuantity.getValue(), "",
                "value is not empty after sending Delete keys, probably setFocus() didn't worked");
    }

    @Test
    public void testSetValue() {
        ProductListForm productListForm = new ProductListForm();
        productListForm.getLblFirstProduct().getMouseActions().moveMouseToElement();
        productListForm.getLblLastProduct().getMouseActions().moveMouseToElement();
        productListForm.getBtnLastProductMore().getJsActions().clickAndWait();

        ProductForm productForm = new ProductForm();
        ITextBox txbQuantity = productForm.getTxbQuantity();
        txbQuantity.getJsActions().setValue("2");
        productForm.getBtnPlus().click();
        Assert.assertEquals(txbQuantity.getValue(), "3",
                "value of textbox is not correct, probably setValue() didn't worked");
    }
}
