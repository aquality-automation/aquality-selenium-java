package aquality.selenium.elements;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.elements.interfaces.IElementSupplier;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.core.waitings.IConditionalWait;
import aquality.selenium.elements.interfaces.*;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;

import java.util.HashMap;
import java.util.Map;

public class ElementFactory extends aquality.selenium.core.elements.ElementFactory implements IElementFactory {

    private final IElementFinder elementFinder;

    @Inject
    public ElementFactory(IConditionalWait conditionalWait, IElementFinder elementFinder, ILocalizationManager localizationManager) {
        super(conditionalWait, elementFinder, localizationManager);
        this.elementFinder = elementFinder;
    }

    private static Map<Class<? extends By>, String> getLocatorToXPathTemplateMap() {
        Map<Class<? extends By>, String> locatorToXPathTemplateMap = new HashMap<>();
        locatorToXPathTemplateMap.put(ByClassName.class, "//*[contains(@class,'%s')]");
        locatorToXPathTemplateMap.put(ByName.class, "//*[@name='%s']");
        locatorToXPathTemplateMap.put(ById.class, "//*[@id='%s']");
        locatorToXPathTemplateMap.put(ByIdOrName.class, "//*[@id='%1$s' or @name='%1$s']");
        return locatorToXPathTemplateMap;
    }

    @Override
    protected Map<Class<? extends aquality.selenium.core.elements.interfaces.IElement>, Class<? extends aquality.selenium.core.elements.interfaces.IElement>> getElementTypesMap() {
        Map<Class<? extends aquality.selenium.core.elements.interfaces.IElement>, Class<? extends aquality.selenium.core.elements.interfaces.IElement>> typesMap = new HashMap<>();
        typesMap.put(IButton.class, Button.class);
        typesMap.put(ICheckBox.class, CheckBox.class);
        typesMap.put(IComboBox.class, ComboBox.class);
        typesMap.put(IMultiChoiceComboBox.class, MultiChoiceComboBox.class);
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
        return isLocatorSupportedForXPathExtraction(multipleElementsLocator)
                ? super.generateXpathLocator(multipleElementsLocator, webElement, elementIndex)
                : By.xpath((String) AqualityServices.getBrowser().executeScript(JavaScript.GET_ELEMENT_XPATH, webElement));
    }

    /**
     * Defines is the locator can be transformed to xpath or not.
     *
     * @param locator locator to transform
     * @return true if the locator can be transformed to xpath, false otherwise.
     */
    @Override
    protected boolean isLocatorSupportedForXPathExtraction(By locator) {
        return getLocatorToXPathTemplateMap().containsKey(locator.getClass())
                || super.isLocatorSupportedForXPathExtraction(locator);
    }

    /**
     * Extracts XPath from passed locator.
     *
     * @param locator locator to get xpath from.
     * @return extracted XPath.
     */
    @Override
    protected String extractXPathFromLocator(By locator) {
        String locatorString = locator.toString();
        int indexOfDots = locatorString.indexOf(':');
        String locValuableString = indexOfDots == -1
                // case ByIdOrName:
                ? locatorString.substring(locatorString.indexOf('"')).replace("\"", "")
                : locatorString.substring(indexOfDots + 1).trim();
        Class<? extends By> locatorClass = locator.getClass();
        return getLocatorToXPathTemplateMap().containsKey(locator.getClass())
                ? String.format(getLocatorToXPathTemplateMap().get(locatorClass), locValuableString)
                : super.extractXPathFromLocator(locator);
    }

    @Override
    protected <T extends aquality.selenium.core.elements.interfaces.IElement> IElementSupplier<T> getDefaultElementSupplier(Class<T> clazz) {
        IElementSupplier<T> baseSupplier = super.getDefaultElementSupplier(clazz);
        return (locator, name, state) -> {
            T element = baseSupplier.get(locator, name, state);
            if (element instanceof Element) {
                ((Element)element).setElementFinder(elementFinder);
            }
            return element;
        };
    }
}
