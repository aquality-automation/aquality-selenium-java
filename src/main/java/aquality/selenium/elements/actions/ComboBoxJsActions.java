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
    @SuppressWarnings("unchecked")
    public List<String> getTexts() {
        logElementAction("loc.combobox.get.texts.js");
        return (List<String>) executeScript(JavaScript.GET_COMBOBOX_TEXTS, element);
    }

    /**
     * Get selected option text
     *
     * @return text of selected element
     */
    public String getSelectedText() {
        logElementAction("loc.combobox.get.text.js");
        return (String) executeScript(JavaScript.GET_COMBOBOX_SELECTED_TEXT, element);
    }

    /**
     * Select value by option's text
     *
     * @param text target option
     */
    public void selectValueByText(final String text) {
        logElementAction("loc.combobox.select.by.text.js", text);
        executeScript(JavaScript.SELECT_COMBOBOX_VALUE_BY_TEXT, element, text);
    }
}