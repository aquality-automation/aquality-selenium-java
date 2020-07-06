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
        logElementAction("loc.checkable.get.state");
        boolean state = getState();
        logElementAction("loc.checkable.state", state);
        return state;
    }

    /**
     * Performs toggle action on the element.
     */
    public void toggle() {
        setState(!getState());
    }

    /**
     * Set value via JavaScript
     *
     * @param state value (true/false)
     */
    private void setState(boolean state) {
        logElementAction("loc.setting.value", state);
        if (state != getState()) {
            click();
        }
    }

    /**
     * @return state of checkbox using .checked property of element
     */
    private boolean getState() {
        return Boolean.valueOf(executeScript(JavaScript.GET_CHECKBOX_STATE, element).toString());
    }
}