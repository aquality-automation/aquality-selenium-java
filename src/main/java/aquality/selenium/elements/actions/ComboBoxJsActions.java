package aquality.selenium.elements.actions;

import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.interfaces.IComboBox;

import java.util.List;
import java.util.stream.Collectors;

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
        List<String> values = (List<String>) executeScript(JavaScript.GET_COMBOBOX_TEXTS);
        logElementAction("loc.combobox.texts",
                values.stream().map(value -> String.format("'%s'", value)).collect(Collectors.joining(", ")));
        return values;
    }

    /**
     * Get selected option text
     *
     * @return text of selected element
     */
    public String getSelectedText() {
        logElementAction("loc.combobox.get.text.js");
        String text = (String) executeScript(JavaScript.GET_COMBOBOX_SELECTED_TEXT);
        logElementAction("loc.combobox.selected.text", text);
        return text;
    }

    /**
     * Select value by option's text
     *
     * @param text target option
     */
    public void selectValueByText(final String text) {
        logElementAction("loc.combobox.select.by.text.js", text);
        executeScript(JavaScript.SELECT_COMBOBOX_VALUE_BY_TEXT, text);
    }
}