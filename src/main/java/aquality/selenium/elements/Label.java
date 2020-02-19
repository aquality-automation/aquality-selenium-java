package aquality.selenium.elements;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class Label extends Element implements ILabel {

    protected Label(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocalizationManager().getLocalizedMessage("loc.label");
    }

}
