package aquality.selenium.elements.interfaces;

public interface IMultiChoiceComboBox extends IComboBox {

    /**
     * Deselect all selected options
     */
    void deselectAll();

    /**
     * Deselect selected option by index
     *
     * @param index number of selected option
     */
    void deselectByIndex(int index);

    /**
     * Deselect selected option by value
     *
     * @param value argument value
     */
    void deselectByValue(String value);

    /**
     * Deselect selected option by containing value
     *
     * @param value partial option's value
     */
    void deselectByContainingValue(String value);

    /**
     * Deselect selected option by visible text
     *
     * @param text text to be deselected
     */
    void deselectByText(String text);

    /**
     * Deselect selected option by containing visible text
     *
     * @param text visible text
     */
    void deselectByContainingText(String text);
}
