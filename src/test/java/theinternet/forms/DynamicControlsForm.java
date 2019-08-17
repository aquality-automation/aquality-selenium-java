package theinternet.forms;

import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class DynamicControlsForm extends Form {

    private final ITextBox txbInput = getElementFactory().getTextBox(By.xpath("//input[@type='text']"), "Input");
    private final IButton btnEnable = getElementFactory().getButton(By.xpath("//button[contains(@onclick, 'swapInput()')][contains(.,'Enable')]"), "Enable");
    private final IButton btnDisable = getElementFactory().getButton(By.xpath("//button[contains(@onclick, 'swapInput()')][contains(.,'Disable')]"), "Disable");
    private final IButton btnRemove = getElementFactory().getButton(By.xpath("//button[contains(@onclick, 'swapCheckbox()')]"), "Remove");
    private final ICheckBox chbACheckbox = getElementFactory().getCheckBox(By.xpath("//div[@id='checkbox']"), "A Checkbox");

    public DynamicControlsForm() {
        super(By.id("content"), "Dynamic Controls");
    }

    public ITextBox getTxbInput() {
        return txbInput;
    }

    public IButton getBtnEnable() {
        return btnEnable;
    }

    public IButton getBtnDisable() {
        return btnDisable;
    }

    public IButton getBtnRemove() {
        return btnRemove;
    }

    public ICheckBox getChbACheckbox() {
        return chbACheckbox;
    }
}
