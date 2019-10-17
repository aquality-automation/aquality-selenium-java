package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ElementsCount;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Defines the interface used to create the elements.
 */
public interface IElementFactory {
    /**
     * Creates element that implements IButton interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements IButton interface
     */
    default IButton getButton(By locator, String name) {
        return getButton(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IButton interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements IButton interface
     */
    IButton getButton(By locator, String name, ElementState state);

    /**
     * Creates element that implements ICheckBox interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements ICheckBox interface
     */
    default ICheckBox getCheckBox(By locator, String name) {
        return getCheckBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ICheckBox interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements ICheckBox interface
     */
    ICheckBox getCheckBox(By locator, String name, ElementState state);

    /**
     * Creates element that implements IComboBox interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements IComboBox interface
     */
    default IComboBox getComboBox(By locator, String name) {
        return getComboBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IComboBox interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements IComboBox interface
     */
    IComboBox getComboBox(By locator, String name, ElementState state);

    /**
     * Creates element that implements ILabel interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements ILabel interface
     */
    default ILabel getLabel(By locator, String name) {
        return getLabel(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ILabel interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements ILabel interface
     */
    ILabel getLabel(By locator, String name, ElementState state);

    /**
     * Creates element that implements ILink interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements ILink interface
     */
    default ILink getLink(By locator, String name) {
        return getLink(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ILink interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements ILink interface
     */
    ILink getLink(By locator, String name, ElementState state);

    /**
     * Creates element that implements IRadioButton interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements IRadioButton interface
     */
    default IRadioButton getRadioButton(By locator, String name) {
        return getRadioButton(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements IRadioButton interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements IRadioButton interface
     */
    IRadioButton getRadioButton(By locator, String name, ElementState state);

    /**
     * Creates element that implements ITextBox interface.
     * @param locator Element locator
     * @param name Element name
     * @return Instance of element that implements ITextBox interface
     */
    default ITextBox getTextBox(By locator, String name) {
        return getTextBox(locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates element that implements ITextBox interface.
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @return Instance of element that implements ITextBox interface
     */
    ITextBox getTextBox(By locator, String name, ElementState state);

    /**
     * Creates an instance of custom element that implements IElement interface.
     * @param supplier Delegate that defines constructor of element
     * @param locator Element locator
     * @param name Element name
     * @param <T> Type of custom element that has to implement IElement
     * @return Instance of custom element that implements IElement interface
     */
    default <T extends IElement> T getCustomElement(IElementSupplier<T> supplier, By locator, String name) {
        return getCustomElement(supplier, locator, name, ElementState.DISPLAYED);
    }

    /**
     * Creates an instance of custom element that implements IElement interface.
     * @param supplier Delegate that defines constructor of element
     * @param locator Element locator
     * @param name Element name
     * @param state Element state
     * @param <T> Type of custom element that has to implement IElement
     * @return Instance of custom element that implements IElement interface
     */
    <T extends IElement> T getCustomElement(IElementSupplier<T> supplier, By locator, String name, ElementState state);

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param clazz class or interface of the element to be obtained
     * @param parentElement parent element for relative search of child element
     * @param state visibility state of target element
     * @return found child element
     */
    <T extends IElement> T findChildElement(IElement parentElement, By childLoc,
                                            Class<? extends IElement> clazz, ElementState state);

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param supplier required element's supplier
     * @param parentElement parent element for relative search of child element
     * @param state visibility state of target element
     * @return found child element
     */
    <T extends IElement> T findChildElement(IElement parentElement, By childLoc,
                                            IElementSupplier<T> supplier, ElementState state);

    /**
     * Find an element in the parent element
     *
     * @param childLoc Child element locator
     * @param type the type of the element to be obtained
     * @param parentElement parent element for relative search of child element
     * @param state visibility state of target elements
     * @return found child element

     */
    <T extends IElement> T findChildElement(IElement parentElement, By childLoc, ElementType type,
                                            ElementState state);

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param supplier required elements' supplier
     * @param count type of expected count of elements
     * @param state visibility state of target elements
     * @return list of elements
     */
    <T extends IElement> List<T> findElements(By locator, IElementSupplier<T> supplier, ElementsCount count,
                                              ElementState state);

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param clazz class or interface of the element to be obtained
     * @param count type of expected count of elements
     * @return list of elements
     */
    <T extends IElement> List<T> findElements(By locator, Class<? extends IElement> clazz, ElementState state, ElementsCount count);

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param type the type of elements to be obtained
     * @param state visibility state of target elements
     * @param count type of expected count of elements
     * @return list of elements
     */
    <T extends IElement> List<T> findElements(By locator, ElementType type, ElementState state, ElementsCount count);

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param clazz class or interface of elements to be obtained
     * @return list of elements
     */
    default <T extends IElement> List<T> findElements(By locator, Class<? extends IElement> clazz) {
        return findElements(locator, clazz, ElementState.DISPLAYED, ElementsCount.MORE_THEN_ZERO);
    }

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param clazz class or interface of elements to be obtained
     * @param count type of expected count of elements
     * @return list of elements
     */
    default <T extends IElement> List<T> findElements(By locator, Class<? extends IElement> clazz, ElementsCount count) {
        return findElements(locator, clazz, ElementState.DISPLAYED, count);
    }

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param type the type of elements to be obtained
     * @return list of elements
     */
    default <T extends IElement> List<T> findElements(By locator, ElementType type) {
        return findElements(locator, type, ElementsCount.MORE_THEN_ZERO);
    }

    /**
     * Find list of elements
     *
     * @param locator Elements selector
     * @param type the type of elements to be obtained
     * @param count type of expected count of elements
     * @return list of elements
     */
    default  <T extends IElement> List<T> findElements(By locator, ElementType type, ElementsCount count) {
        return findElements(locator, type, ElementState.DISPLAYED, count);
    }
}
