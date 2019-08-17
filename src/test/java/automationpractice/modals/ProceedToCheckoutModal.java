package automationpractice.modals;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProceedToCheckoutModal extends Form {

    private static final By proceedToCkeckout = By.xpath("//a[@title='Proceed to checkout']");

    public ProceedToCheckoutModal() {
        super(proceedToCkeckout, "Proceed To Checkout Modal");
    }

    public IButton getBtnProceedToCheckout(){
        return getElementFactory().getButton(By.xpath("//a[@title='Proceed to checkout']"), "Proceed Checkout");
    }
}
