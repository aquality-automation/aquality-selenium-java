package automationpractice.forms;

import aquality.selenium.forms.Form;
import aquality.selenium.elements.interfaces.IButton;
import org.openqa.selenium.By;

public class CartMenuForm extends Form {

    private final IButton btnProducts = getElementFactory().getButton(By.xpath("//span[@class='ajax_cart_product_txt_s']"), "Products");
    private final IButton btnOrder = getElementFactory().getButton(By.xpath("//a[@id='button_order_cart']"), "Order Card");

    public CartMenuForm() {
        super(By.id("button_order_cart"), "Cart Menu");
    }

    public void openCartMenu(){
        btnProducts.getMouseActions().moveMouseToElement();
    }

    public void clickCheckoutBtn(){
        btnOrder.clickAndWait();
    }
}
