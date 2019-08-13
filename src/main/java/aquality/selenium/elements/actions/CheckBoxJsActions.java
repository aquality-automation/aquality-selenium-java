package aquality.selenium.elements.actions;

import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.ICheckBox;

public class CheckBoxJsActions extends JsActions {

    public CheckBoxJsActions(ICheckBox checkBox, String elementType, String name) {
        super(checkBox, elementType, name);
    }

    /**
     * @return state of checkbox using .checked property of element
     */
    public boolean getState() {
        infoLoc("loc.checkbox.get.state");
        return Boolean.valueOf(executeScript(JavaScript.GET_CHECKBOX_STATE, element).toString());
    }

    public void check() {
        setState(true);
    }

    public void uncheck() {
        setState(false);
    }

    public boolean isChecked() {
        return getState();
    }

    public void toggle() {
        setState(!isChecked());
    }

    /**
     * Set value via JavaScript
     *
     * @param state value (true/false)
     */
    private void setState(boolean state) {
        infoLoc(String.format("%1$s '%2$s'", localizationManager.getValue("loc.setting.value"), state));
        if (state && !getState()) {
            infoLoc(String.format(localizationManager.getValue("loc.checkbox.check"), name, type, true));
            click();
        } else if (!state && getState()) {
            infoLoc(String.format(localizationManager.getValue("loc.checkbox.uncheck"), name, type, false));
            click();
        }
    }
}