package automationpractice.forms;

import aquality.selenium.elements.Attributes;
import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ShoppingCardSummaryForm extends Form {

    private static final String TMP_QUANTITY_XPATH = "//input[contains(@name, 'quantity')][@value='%1$s']";
    private final IButton btnProceedToStandardCheckout = getElementFactory().getButton(By.xpath("//a[@class='button btn btn-default standard-checkout button-medium']"), "Proceed Checkout");
    private final IButton btnPlus = getElementFactory().getButton(By.xpath("//i[@class='icon-plus']"), "Plus");

    public ShoppingCardSummaryForm() {
        super(By.xpath("//input[contains(@name, 'quantity')]"), "Shopping Card Summary");
    }

    public IButton getBtnPlus(){
        return btnPlus;
    }

    public Integer waitForQuantityAndGetValue(Integer expectedQuantity){
        ILabel lblQuantity = getElementFactory().getLabel(By.xpath(String.format(TMP_QUANTITY_XPATH, expectedQuantity)),"Count of Items", ElementState.EXISTS_IN_ANY_STATE);
        return Integer.valueOf(lblQuantity.getAttribute(Attributes.VALUE.toString()));
    }

    public void clickProceedToCheckoutBtn(){
        btnProceedToStandardCheckout.getJsActions().click();
    }
}
