package automationpractice.modals;

import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

@PageInfo(id = "//a[@title='Proceed to checkout']", pageName = "Procced To Checkout Modal")
public class ProceedToCheckoutModal extends Form {

    public IButton getBtnProceedToCheckout(){
        return getElementFactory().getButton(By.xpath("//a[@title='Proceed to checkout']"), "Proceed Checkout");
    }
}
