package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.ILink;
import org.openqa.selenium.By;

/**
 * The class that describes the link
 */
public class Link extends Element implements ILink {

    protected Link(final By locator, final String name, final ElementState state) {
        super(locator, name, state);
    }

    protected String getElementType() {
        return getLocManager().getValue("loc.link");
    }

    @Override
    public String getHref() {
        return getAttribute("href");
    }
}
