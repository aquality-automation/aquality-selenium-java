package aquality.selenium.elements;

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
        return getLocManager().getValue("loc.checkbox");
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
        return new CheckBoxJsActions(this, getElementType(), getName());
    }

    /**
     * Set value
     *
     * @param state value (true/false)
     */
    private void setState(boolean state) {
        getLogger().info(String.format("%1$s '%2$s'", getLocManager().getValue("loc.setting.value"), state));
        if (state && !getState()) {
            getLogger().info(getLocManager().getValue("loc.checkbox.check"), getName(), getElementType(), true);
            click();
        } else if (!state && getState()) {
            getLogger().info(getLocManager().getValue("loc.checkbox.uncheck"), getName(), getElementType(), false);
            click();
        }
    }

    private boolean getState() {
        info(getLocManager().getValue("loc.checkbox.get.state"));
        return getElement().isSelected();
    }
}
