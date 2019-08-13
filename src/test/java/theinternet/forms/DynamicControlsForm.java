package theinternet.forms;

import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.forms.PageInfo;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

@PageInfo(id = "content", pageName = "Dynamic Controls")
public class DynamicControlsForm extends Form {

    private final ITextBox txbInput = getElementFactory().getTextBox(By.xpath("//input[@type='text']"), "Input");
    private final IButton btnEnable = getElementFactory().getButton(By.xpath("//button[contains(@onclick, 'swapInput()')]"), "Enable");
    private final IButton btnRemove = getElementFactory().getButton(By.xpath("//button[contains(@onclick, 'swapCheckbox()')]"), "Remove");
    private final ICheckBox chbACheckbox = getElementFactory().getCheckBox(By.xpath("//div[@id='checkbox']"), "A Checkbox");


    public ITextBox getTxbInput() {
        return txbInput;
    }

    public IButton getBtnEnable() {
        return btnEnable;
    }

    public IButton getBtnRemove() {
        return btnRemove;
    }

    public ICheckBox getChbACheckbox() {
        return chbACheckbox;
    }
}
