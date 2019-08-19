package automationpractice.forms;

import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class DropDownForm extends Form {

    private IComboBox comboBox = getElementFactory().getComboBox(By.id("dropdown"), "Dropdown");

    public DropDownForm() {
        super(By.id("content"), "Drop Down");
    }

    public IComboBox getComboBox() {
        return comboBox;
    }

}
