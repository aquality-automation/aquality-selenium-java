package aquality.selenium.elements.interfaces;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Defines the interface used to create the elements.
 */
public interface IElementFactory extends aquality.selenium.core.elements.interfaces.IElementFactory {
    /**
     * Creates element that implements IButton interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements IButton interface
     */
    default IButton getButton(By locator, String name) {
        return getButton(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IButton interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements IButton interface
     */
    default IButton getButton(By locator, String name, ElementState state) {
        return get(ElementType.BUTTON, locator, name, state);
    }

    /**
     * Creates element that implements ICheckBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements ICheckBox interface
     */
    default ICheckBox getCheckBox(By locator, String name) {
        return getCheckBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ICheckBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements ICheckBox interface
     */
    default ICheckBox getCheckBox(By locator, String name, ElementState state) {
        return get(ElementType.CHECKBOX, locator, name, state);
    }

    /**
     * Creates element that implements IComboBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements IComboBox interface
     */
    default IComboBox getComboBox(By locator, String name) {
        return getComboBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IComboBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements IComboBox interface
     */
    default IComboBox getComboBox(By locator, String name, ElementState state) {
        return get(ElementType.COMBOBOX, locator, name, state);
    }

    /**
     * Creates element that implements ILabel interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements ILabel interface
     */
    default ILabel getLabel(By locator, String name) {
        return getLabel(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ILabel interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements ILabel interface
     */
    default ILabel getLabel(By locator, String name, ElementState state) {
        return get(ElementType.LABEL, locator, name, state);
    }

    /**
     * Creates element that implements ILink interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements ILink interface
     */
    default ILink getLink(By locator, String name) {
        return getLink(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ILink interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements ILink interface
     */
    default ILink getLink(By locator, String name, ElementState state) {
        return get(ElementType.LINK, locator, name, state);
    }

    /**
     * Creates element that implements IRadioButton interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements IRadioButton interface
     */
    default IRadioButton getRadioButton(By locator, String name) {
        return getRadioButton(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IRadioButton interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements IRadioButton interface
     */
    default IRadioButton getRadioButton(By locator, String name, ElementState state) {
        return get(ElementType.RADIOBUTTON, locator, name, state);
    }

    /**
     * Creates element that implements ITextBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @return Instance of element that implements ITextBox interface
     */
    default ITextBox getTextBox(By locator, String name) {
        return getTextBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ITextBox interface.
     *
     * @param locator Element locator
     * @param name    Element name
     * @param state   Element state
     * @return Instance of element that implements ITextBox interface
     */
    default ITextBox getTextBox(By locator, String name, ElementState state) {
        return get(ElementType.TEXTBOX, locator, name, state);
    }

    /**
     * Create element according to passed parameters.
     *
     * @param type    Type of the element to be obtained
     * @param locator Locator of the target element.
     * @param name    Name of the target element.
     * @param state   Visibility state of the target element.
     * @param <T>     Type of the target element.
     * @return Instance of custom element.
     */
    default <T extends IElement> T get(ElementType type, By locator, String name, ElementState state) {
        return getCustomElement(type.getClazz(), locator, name, state);
    }

    /**
     * Create element according to passed parameters.
     *
     * @param type    Type of the element to be obtained
     * @param locator Locator of the target element.
     * @param name    Name of the target element.
     * @param <T>     Type of the target element.
     * @return Instance of custom element.
     */
    default <T extends IElement> T get(ElementType type, By locator, String name) {
        return get(type, locator, name, ElementState.DISPLAYED);
    }

    /**
     * Find an element in the parent element
     *
     * @param childLoc      Child element locator
     * @param type          Type of the element to be obtained
     * @param name          Child element name.
     * @param parentElement parent element for relative search of child element
     * @param state         visibility state of target elements
     * @param <T>           Type of the target element.
     * @return found child element
     */
    default <T extends IElement> T findChildElement(IElement parentElement, By childLoc, String name, ElementType type,
                                                    ElementState state) {
        return findChildElement(parentElement, childLoc, name, type.getClazz(), state);
    }

    /**
     * Finds child element in any state by its locator relative to parent element.
     *
     * @param childLoc      Locator of child element relative to its parent.
     * @param type          Type of the element to be obtained
     * @param name          Child element name.
     * @param parentElement Parent element for relative search of child element.
     * @param <T>           Type of the target element.
     * @return Child element.
     */
    default <T extends IElement> T findChildElement(IElement parentElement, By childLoc, String name,
                                                    ElementType type) {
        return findChildElement(parentElement, childLoc, name, type, ElementState.EXISTS_IN_ANY_STATE);
    }

    /**
     * Finds child element by its locator relative to parent element.
     *
     * @param childLoc      Locator of child element relative to its parent.
     * @param type          Type of the element to be obtained
     * @param parentElement Parent element for relative search of child element.
     * @param state         Visibility state of child element.
     * @param <T>           Type of the target element.
     * @return Child element.
     */
    default <T extends IElement> T findChildElement(IElement parentElement, By childLoc,
                                                    ElementType type, ElementState state) {
        return findChildElement(parentElement, childLoc, null, type, state);
    }

    /**
     * Finds child element existing in any state by its locator relative to parent element.
     *
     * @param childLoc      Locator of child element relative to its parent.
     * @param type          Type of the element to be obtained
     * @param parentElement Parent element for relative search of child element.
     * @param <T>           Type of the target element.
     * @return Child element.
     */
    default <T extends IElement> T findChildElement(IElement parentElement, By childLoc, ElementType type) {
        return findChildElement(parentElement, childLoc, null, type, ElementState.EXISTS_IN_ANY_STATE);
    }

    /**
     * Find list of elements
     *
     * @param <T>     Type of the target elements.
     * @param locator Elements selector
     * @param name    elements' name.
     * @param type    Type of elements to be obtained
     * @param state   visibility state of target elements
     * @param count   type of expected count of elements
     * @return list of elements
     */
    default <T extends IElement> List<T> findElements(By locator, String name, ElementType type, ElementsCount count,
                                                      ElementState state) {
        return findElements(locator, name, type.getClazz(), count, state);
    }

    /**
     * Find list of elements.
     *
     * @param locator Elements selector.
     * @param type    Type of elements to be obtained
     * @param count   Expected number of elements that have to be found (zero, more then zero, any).
     * @param state   Visibility state of target elements.
     * @param <T>     Type of the target element.
     * @return List of elements.
     */
    default <T extends IElement> List<T> findElements(By locator, ElementType type, ElementsCount count,
                                                      ElementState state) {
        return findElements(locator, null, type, count, state);
    }

    /**
     * Find list of elements.
     *
     * @param locator Elements selector.
     * @param type    Type of elements to be obtained
     * @param <T>     Type of the target element.
     * @return List of elements.
     */
    default <T extends IElement> List<T> findElements(By locator, ElementType type) {
        return findElements(locator, type, ElementsCount.ANY, ElementState.DISPLAYED);
    }

    /**
     * Find list of elements.
     *
     * @param locator Elements selector.
     * @param type    Type of elements to be obtained
     * @param name    Child element name.
     * @param count   Expected number of elements that have to be found (zero, more then zero, any).
     * @param <T>     Type of the target element.
     * @return List of elements.
     */
    default <T extends IElement> List<T> findElements(By locator, String name, ElementType type, ElementsCount count) {
        return findElements(locator, name, type, count, ElementState.DISPLAYED);
    }

    /**
     * Find list of elements.
     *
     * @param locator Elements selector.
     * @param name    Child element name.
     * @param type    Type of elements to be obtained
     * @param <T>     Type of the target element.
     * @return List of elements.
     */
    default <T extends IElement> List<T> findElements(By locator, String name, ElementType type) {
        return findElements(locator, name, type, ElementsCount.ANY);
    }

    /**
     * Find list of elements.
     *
     * @param locator Elements selector.
     * @param type    Type of elements to be obtained
     * @param count   Expected number of elements that have to be found (zero, more then zero, any).
     * @param <T>     Type of the target element.
     * @return List of elements.
     */
    default <T extends IElement> List<T> findElements(By locator, ElementType type, ElementsCount count) {
        return findElements(locator, type, count, ElementState.DISPLAYED);
    }


    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param type          Type of elements to be obtained
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, ElementType type) {
        return findChildElements(parentElement, childLoc, type, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param type          Type of elements to be obtained
     * @param count         Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, ElementType type,
                                                           ElementsCount count) {
        return findChildElements(parentElement, childLoc, type, count, ElementState.DISPLAYED);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param type          Type of elements to be obtained
     * @param state         Visibility state of child elements.
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc,
                                                           ElementType type, ElementState state) {
        return findChildElements(parentElement, childLoc, type, ElementsCount.ANY, state);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param type          Type of the element to be obtained
     * @param count         Expected number of elements that have to be found (zero, more then zero, any).
     * @param state         Visibility state of child elements.
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, ElementType type,
                                                           ElementsCount count, ElementState state) {
        return findChildElements(parentElement, childLoc, null, type, count, state);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param name          Child elements name.
     * @param type          Type of the element to be obtained
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc,
                                                           String name, ElementType type) {
        return findChildElements(parentElement, childLoc, name, type, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param name          Child elements name.
     * @param type          Type of the element to be obtained
     * @param count         Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, String name,
                                                           ElementType type, ElementsCount count) {
        return findChildElements(parentElement, childLoc, name, type, count, ElementState.DISPLAYED);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>           Type of the target elements.
     * @param parentElement Parent element for relative search of child elements.
     * @param childLoc      Locator of child elements relative to its parent.
     * @param name          Child elements name.
     * @param type          Type of the element to be obtained
     * @param state         Visibility state of child elements.
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, String name,
                                                           ElementType type, ElementState state) {
        return findChildElements(parentElement, childLoc, name, type, ElementsCount.ANY, state);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param childLoc      Locator of child elements relative to its parent.
     * @param type          Type of the element to be obtained
     * @param name          Child elements name.
     * @param parentElement Parent element for relative search of child elements.
     * @param count         Expected number of elements that have to be found (zero, more then zero, any).
     * @param state         Visibility state of target elements.
     * @param <T>           Type of the target elements.
     * @return List of child elements.
     */
    default <T extends IElement> List<T> findChildElements(IElement parentElement, By childLoc, String name,
                                                           ElementType type, ElementsCount count, ElementState state) {
        return findChildElements(parentElement, childLoc, name, type.getClazz(), count, state);
    }
}
