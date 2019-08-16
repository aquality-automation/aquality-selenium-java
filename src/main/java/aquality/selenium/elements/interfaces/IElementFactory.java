package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ElementsCount;
import org.openqa.selenium.By;

import java.util.List;

public interface IElementFactory {
    default IButton getButton(By locator, String name) {
        return getButton(locator, name, ElementState.DISPLAYED);
    }

    IButton getButton(By locator, String name, ElementState state);

    default ICheckBox getCheckBox(By locator, String name) {
        return getCheckBox(locator, name, ElementState.DISPLAYED);
    }

    ICheckBox getCheckBox(By locator, String name, ElementState state);

    default IComboBox getComboBox(By locator, String name) {
        return getComboBox(locator, name, ElementState.DISPLAYED);
    }

    IComboBox getComboBox(By locator, String name, ElementState state);

    default ILabel getLabel(By locator, String name) {
        return getLabel(locator, name, ElementState.DISPLAYED);
    }

    ILabel getLabel(By locator, String name, ElementState state);

    default ILink getLink(By locator, String name) {
        return getLink(locator, name, ElementState.DISPLAYED);
    }

    ILink getLink(By locator, String name, ElementState state);

    default IRadioButton getRadioButton(By locator, String name) {
        return getRadioButton(locator, name, ElementState.DISPLAYED);
    }

    IRadioButton getRadioButton(By locator, String name, ElementState state);

    default ITextBox getTextBox(By locator, String name) {
        return getTextBox(locator, name, ElementState.DISPLAYED);
    }

    ITextBox getTextBox(By locator, String name, ElementState state);

    default <T extends IElement> T getCustomElement(IElementSupplier<T> supplier, By locator, String name) {
        return getCustomElement(supplier, locator, name, ElementState.DISPLAYED);
    }

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
