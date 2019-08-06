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
}