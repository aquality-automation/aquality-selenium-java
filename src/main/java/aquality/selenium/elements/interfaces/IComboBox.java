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
     * @param text text to be selected
     */
    void selectByText(String text);

    /**
     * Open Dropdown and select by visible text
     *
     * @param value text to be selected
     */
    void clickAndSelectByText(String value);

    /**
     * Select by containing visible text
     *
     * @param text visible text
     */
    void selectByContainingText(String text);

    /**
     * Select by containing value
     *
     * @param value partial option's value
     */
    void selectByContainingValue(String value);

    /**
     * Select by value
     *
     * @param value argument value
     */
    void selectByValue(String value);

    /**
     * Open Dropdown and select by value
     *
     * @param value argument value
     */
    void clickAndSelectByValue(String value);

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
    List<String> getValues();

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
