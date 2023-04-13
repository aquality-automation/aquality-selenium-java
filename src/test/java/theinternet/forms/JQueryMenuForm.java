package theinternet.forms;

import aquality.selenium.elements.Attributes;
import aquality.selenium.elements.interfaces.IButton;
import org.openqa.selenium.By;

public class JQueryMenuForm extends TheInternetForm {
    private final IButton btnEnabled = getElementFactory().getButton(By.id("ui-id-2"), "Enabled");

    public JQueryMenuForm(){
        super(By.id("menu"), "JQueryUI menu");
    }

    public IButton getEnabledButton() {
        return btnEnabled;
    }

    public boolean isEnabledButtonFocused() {
        return btnEnabled.getAttribute(Attributes.CLASS.toString()).contains("focus");
    }

    @Override
    protected String getUri() {
        return "/jqueryui/menu";
    }
}
