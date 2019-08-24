package automationpractice.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProductForm extends Form {

    private final ITextBox txbQuantity = getElementFactory().getTextBox(By.id("quantity_wanted"), "quantity");
    private final IButton btnPlus = getElementFactory().getButton(By.xpath("//a[contains(@class, 'button-plus')]"), "Plus");

    public ProductForm() {
        super(By.id("quantity_wanted"), "Product");
    }

    public ITextBox getTxbQuantity() {
        return txbQuantity;
    }

    public IButton getBtnPlus() {
        return btnPlus;
    }
}
