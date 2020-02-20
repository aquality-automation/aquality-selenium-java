package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IRadioButton;
import org.openqa.selenium.By;

/**
 * Class describing the Radiobutton
 */
public class RadioButton extends Element implements IRadioButton {

    protected RadioButton(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.radio");
    }

    @Override
    public boolean isChecked() {
        return doWithRetry(() -> getElement().isSelected());
    }
}
