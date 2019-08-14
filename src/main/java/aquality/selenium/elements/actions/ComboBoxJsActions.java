package aquality.selenium.elements.actions;

import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.IComboBox;

import java.util.List;

public class ComboBoxJsActions extends JsActions {

    public ComboBoxJsActions(IComboBox comboBox, String elementType) {
        super(comboBox, elementType);
    }

    /**
     * Values from ComboBox by js
     *
     * @return values from ComboBox
     */
    public List<String> getValues() {
        return (List<String>) executeScript(JavaScript.GET_COMBOBOX_VALUES, element);
    }

    /**
     * Get selected option text
     *
     * @return text of selected element
     */
    public String getSelectedText() {
        return (String) executeScript(JavaScript.GET_COMBOBOX_TEXT, element);
    }

    /**
     * Select value by option's text
     *
     * @param text target option
     */
    public void selectValueByText(final String text) {
        executeScript(JavaScript.SELECT_COMBOBOX_VALUE_BY_TEXT, element, text);
    }
}