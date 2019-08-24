package aquality.selenium.elements.actions;

import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.IComboBox;

import java.util.List;

public class ComboBoxJsActions extends JsActions {

    public ComboBoxJsActions(IComboBox comboBox, String elementType) {
        super(comboBox, elementType);
    }

    /**
     * Texts of options from ComboBox by js
     *
     * @return texts of options from ComboBox
     */
    public List<String> getTexts() {
        return (List<String>) executeScript(JavaScript.GET_COMBOBOX_TEXTS, element);
    }

    /**
     * Get selected option text
     *
     * @return text of selected element
     */
    public String getSelectedText() {
        return (String) executeScript(JavaScript.GET_COMBOBOX_SELECTED_TEXT, element);
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