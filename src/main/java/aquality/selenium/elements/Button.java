package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import org.openqa.selenium.By;

/**
 * Class describing element button
 */
public class Button extends Element implements IButton {

    protected Button(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.button");
    }
}
