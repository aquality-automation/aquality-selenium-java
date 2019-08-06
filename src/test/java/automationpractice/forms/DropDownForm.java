package automationpractice.forms;

import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IComboBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

@PageInfo(id = "dropdown", pageName = "Dropdown")
public class DropDownForm extends Form {

    private IComboBox comboBox = getElementFactory().getComboBox(By.id("dropdown"), "Dropdown");

    public IComboBox getComboBox() {
        return comboBox;
    }

}
