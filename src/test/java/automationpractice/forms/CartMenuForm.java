package automationpractice.forms;

import aquality.selenium.forms.Form;
import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IButton;
import org.openqa.selenium.By;

@PageInfo(xpath = "//span[@class='ajax_cart_product_txt_s']", pageName = "Cart Menu")
public class CartMenuForm extends Form {

    private final IButton btnProducts = getElementFactory().getButton(By.xpath("//span[@class='ajax_cart_product_txt_s']"), "Products");
    private final IButton btnOrder = getElementFactory().getButton(By.xpath("//a[@id='button_order_cart']"), "Order Card");

    public void openCartMenu(){
        btnProducts.getMouseActions().moveMouseToElement();
    }

    public void clickCheckoutBtn(){
        btnOrder.clickAndWait();
    }
}
