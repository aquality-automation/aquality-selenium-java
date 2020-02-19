package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.actions.CheckBoxJsActions;
import aquality.selenium.elements.interfaces.ICheckBox;
import org.openqa.selenium.By;

/**
 * Class describing the checkbox
 */
public class CheckBox extends Element implements ICheckBox {

    protected CheckBox(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.checkbox");
    }

    @Override
    public void check() {
        setState(true);
    }

    @Override
    public void uncheck() {
        setState(false);
    }

    @Override
    public boolean isChecked() {
        return getState();
    }

    @Override
    public void toggle() {
        setState(!isChecked());
    }

    @Override
    public CheckBoxJsActions getJsActions() {
        return new CheckBoxJsActions(this, getElementType());
    }

    /**
     * Set value
     *
     * @param state value (true/false)
     */
    private void setState(boolean state) {
        logElementAction("loc.setting.value", state);
        if (state != getState()) {
            click();
        }
    }

    private boolean getState() {
        logElementAction("loc.checkbox.get.state");
        return getElement().isSelected();
    }
}
