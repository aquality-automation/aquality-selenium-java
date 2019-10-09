package aquality.selenium.elements.actions;

import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.ICheckBox;

/**
 * Allows to perform actions on elements via JavaScript specific for CheckBoxes.
 */
public class CheckBoxJsActions extends JsActions {

    public CheckBoxJsActions(ICheckBox checkBox, String elementType) {
        super(checkBox, elementType);
    }

    /**
     * @return state of checkbox using .checked property of element
     */
    public boolean getState() {
        infoLoc("loc.checkbox.get.state");
        return Boolean.valueOf(executeScript(JavaScript.GET_CHECKBOX_STATE, element).toString());
    }

    /**
     * Performs check action on the element.
     */
    public void check() {
        setState(true);
    }

    /**
     * Performs uncheck action on the element.
     */
    public void uncheck() {
        setState(false);
    }

    /**
     * Get status if element is checked
     * @return true if checked, false otherwise
     */
    public boolean isChecked() {
        return getState();
    }

    /**
     * Performs toggle action on the element.
     */
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
        if (state != getState()) {
            click();
        }
    }
}