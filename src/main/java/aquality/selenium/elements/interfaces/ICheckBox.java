package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.actions.CheckBoxJsActions;

public interface ICheckBox extends IElement {
    /**
     * set true
     */
    void check();

    /**
     * Get the value of the checkbox (true / false)
     * @return true if is checked, false otherwise
     */
    boolean isChecked();

    /**
     * reverse state
     */
    void toggle();

    /**
     * Set the checkbox to false
     */
    void uncheck();

    /**
     * Gets {@link CheckBoxJsActions} object designed to perform javascript actions
     * @return {@link CheckBoxJsActions} object
     */
    CheckBoxJsActions getJsActions();
}
