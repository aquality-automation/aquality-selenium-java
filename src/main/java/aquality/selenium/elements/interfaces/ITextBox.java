package aquality.selenium.elements.interfaces;

public interface ITextBox extends IElement {
    /**
     * Enter the text in the box
     *
     * @param value text
     */
    void type(String value);

    /**
     * Enter the text in the box, inputted value isn't logging
     *
     * @param value text
     */
    void typeSecret(String value);

    /**
     * Clears input and enters text in the box, inputted value isn't logging
     *
     * @param value text
     */
    void clearAndType(String value);

    /**
     * Clears input and enters text in the box
     *
     * @param value text
     */
    void clearAndTypeSecret(String value);

    /**
     * Submits the field
     */
    void submit();

    /**
     * Gets value of field
     *
     * @return value
     */
    String getValue();

    /**
     * Focuses on the element using send keys
     */
    void focus();

    /**
     * Removing focus from the element using send keys
     */
    void unfocus();
}
