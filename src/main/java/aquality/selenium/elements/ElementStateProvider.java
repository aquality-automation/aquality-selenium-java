package aquality.selenium.elements;

import aquality.selenium.core.elements.DefaultElementStateProvider;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.waitings.IConditionalWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ElementStateProvider extends DefaultElementStateProvider {
    public ElementStateProvider(By locator, IConditionalWait conditionalWait, IElementFinder elementFinder) {
        super(locator, conditionalWait, elementFinder);
    }

    @Override
    protected boolean isElementEnabled(WebElement element) {
        return element.isEnabled() && !element.getAttribute(Attributes.CLASS.toString()).contains("disabled");
    }
}
