package aquality.selenium.elements;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.waitings.IConditionalWait;
import aquality.selenium.elements.interfaces.*;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class ElementFactory extends aquality.selenium.core.elements.ElementFactory implements IElementFactory {

    @Inject
    public ElementFactory(IConditionalWait conditionalWait, IElementFinder elementFinder, ILocalizationManager localizationManager) {
        super(conditionalWait, elementFinder, localizationManager);
    }

    @Override
    protected Map<Class<? extends aquality.selenium.core.elements.interfaces.IElement>, Class<? extends aquality.selenium.core.elements.interfaces.IElement>> getElementTypesMap() {
        Map<Class<? extends aquality.selenium.core.elements.interfaces.IElement>, Class<? extends aquality.selenium.core.elements.interfaces.IElement>> typesMap = new HashMap<>();
        typesMap.put(IButton.class, Button.class);
        typesMap.put(ICheckBox.class, CheckBox.class);
        typesMap.put(IComboBox.class, ComboBox.class);
        typesMap.put(ILabel.class, Label.class);
        typesMap.put(ILink.class, Link.class);
        typesMap.put(IRadioButton.class, RadioButton.class);
        typesMap.put(ITextBox.class, TextBox.class);
        return typesMap;
    }

    /**
     * Generates xpath locator for target element.
     *
     * @param multipleElementsLocator locator used to find elements.
     * @param webElement              target element.
     * @param elementIndex            index of target element.
     * @return target element's locator
     */
    @Override
    protected By generateXpathLocator(By multipleElementsLocator, WebElement webElement, int elementIndex) {
        return multipleElementsLocator.getClass().equals(ByXPath.class)
                ? super.generateXpathLocator(multipleElementsLocator, webElement, elementIndex)
                : By.xpath((String) AqualityServices.getBrowser().executeScript(JavaScript.GET_ELEMENT_XPATH, webElement));
    }
}
