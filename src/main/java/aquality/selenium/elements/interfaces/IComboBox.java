package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.actions.ComboBoxJsActions;

import java.util.List;

public interface IComboBox extends IElement {
    /**
     * Select by index
     *
     * @param index number of selected option
     */
    void selectByIndex(int index);

    /**
     * Select by visible text
     *
     * @param value text to be selected
     */
    void selectByText(String value);

    /**
     * Select by containing visible text
     *
     * @param text visible text
     */
    void selectOptionThatContainsText(String text);

    /**
     * Select by containing value
     *
     * @param value partial option's value
     */
    void selectOptionThatContainsValue(String value);

    /**
     * Select by value
     *
     * @param value argument value
     */
    void selectByValue(String value);

    /**
     * Get selected value
     *
     * @return selected value
     */
    String getSelectedText();


    /**
     * Get values list
     * @return list of values
     */
    List<String> getValuesList();

    /**
     * Get text from selected option in combobox using JavaScript
     *
     * @return element's text
     */
    String getSelectedTextByJs();

    /**
     * Get {@link ComboBoxJsActions} object to access to available
     * javascript actions with current element.
     *
     * @return {@link ComboBoxJsActions}
     */
    ComboBoxJsActions getJsActions();
}
