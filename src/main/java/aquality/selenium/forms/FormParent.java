package aquality.selenium.forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.core.elements.interfaces.IElementSupplier;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Abstraction to support finding elements relative to form element.
 * Implements same methods as defined for
 *
 * @see IElement, but with protected access to them.
 */
abstract class FormParent {

    /**
     * Gets Label of form element defined by its locator and name.
     *
     * @return Label of form element.
     */
    protected abstract ILabel getFormLabel();

    /**
     * Finds an element relative to current form
     *
     * @param childLoc child element locator relative to form locator
     * @param name     output name in logs
     * @param clazz    class or interface of the element to be obtained
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, String name, Class<T> clazz, ElementState state) {
        return getFormLabel().findChildElement(childLoc, name, clazz, state);
    }

    /**
     * Finds an element relative to current form with DISPLAYED state
     *
     * @param childLoc child element locator relative to form locator
     * @param name     output name in logs
     * @param clazz    class or interface of the element to be obtained
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, String name, Class<T> clazz) {
        return findChildElement(childLoc, name, clazz, ElementState.DISPLAYED);
    }

    /**
     * Finds an element relative to current form
     *
     * @param childLoc child element locator relative to form locator
     * @param clazz    class or interface of the element to be obtained
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, Class<T> clazz, ElementState state) {
        return findChildElement(childLoc, null, clazz, state);
    }

    /**
     * Finds an element relative to current form with DISPLAYED state
     *
     * @param childLoc child element locator relative to form locator
     * @param clazz    class or interface of the element to be obtained
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, Class<T> clazz) {
        return findChildElement(childLoc, null, clazz, ElementState.DISPLAYED);
    }

    /**
     * Finds an element relative to current form
     *
     * @param childLoc Child element locator
     * @param name     output name in logs
     * @param supplier required element's supplier
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    <T extends IElement> T findChildElement(By childLoc, String name, IElementSupplier<T> supplier, ElementState state) {
        return getFormLabel().findChildElement(childLoc, name, supplier, state);
    }

    /**
     * Finds an element relative to current form with DISPLAYED state
     *
     * @param childLoc child element locator relative to form locator
     * @param name     output name in logs
     * @param supplier required element's supplier
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, String name, IElementSupplier<T> supplier) {
        return findChildElement(childLoc, name, supplier, ElementState.DISPLAYED);
    }

    /**
     * Finds an element relative to current form
     *
     * @param childLoc child element locator relative to form locator
     * @param supplier required element's supplier
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, IElementSupplier<T> supplier, ElementState state) {
        return findChildElement(childLoc, null, supplier, state);
    }

    /**
     * Finds an element relative to current form with DISPLAYED state
     *
     * @param childLoc child element locator relative to form locator
     * @param supplier required element's supplier
     * @param <T>      the type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, IElementSupplier<T> supplier) {
        return findChildElement(childLoc, null, supplier, ElementState.DISPLAYED);
    }

    /**
     * Find an element in the parent element
     *
     * @param childLoc    child element locator
     * @param name        output name in logs
     * @param elementType type of the element to be obtained
     * @param state       visibility state of target element
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, String name, ElementType elementType,
                                                      ElementState state) {
        return getFormLabel().findChildElement(childLoc, name, elementType, state);
    }

    /**
     * Find an element in the parent element with protected name.
     *
     * @param childLoc    child element locator
     * @param elementType type of the element to be obtained
     * @param state       visibility state of target element
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, ElementType elementType, ElementState state) {
        return findChildElement(childLoc, null, elementType, state);
    }

    /**
     * Find an element in the parent element with DISPLAYED state and protected name.
     *
     * @param childLoc    child element locator
     * @param elementType type of the element to be obtained
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, ElementType elementType) {
        return findChildElement(childLoc, null, elementType, ElementState.DISPLAYED);
    }

    /**
     * Find an element in the parent element with DISPLAYED state
     *
     * @param childLoc    child element locator
     * @param name        output name in logs
     * @param elementType type of the element to be obtained
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    protected <T extends IElement> T findChildElement(By childLoc, String name, ElementType elementType) {
        return findChildElement(childLoc, name, elementType, ElementState.DISPLAYED);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param clazz    Class or interface of the elements to be obtained.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, Class<T> clazz) {
        return findChildElements(childLoc, clazz, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, Class<T> clazz, ElementsCount count) {
        return findChildElements(childLoc, clazz, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param state    Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, Class<T> clazz, ElementState state) {
        return findChildElements(childLoc, clazz, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param state    Visibility state of child elements.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, Class<T> clazz, ElementState state,
                                                             ElementsCount count) {
        return findChildElements(childLoc, null, clazz, state, count);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param clazz    Class or interface of the elements to be obtained.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, Class<T> clazz) {
        return findChildElements(childLoc, name, clazz, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, Class<T> clazz,
                                                             ElementsCount count) {
        return findChildElements(childLoc, name, clazz, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param state    Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, Class<T> clazz,
                                                             ElementState state) {
        return findChildElements(childLoc, name, clazz, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param clazz    Class or interface of the elements to be obtained.
     * @param state    Visibility state of target elements.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, Class<T> clazz,
                                                             ElementState state, ElementsCount count) {
        return getFormLabel().findChildElements(childLoc, name, clazz, state, count);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param supplier Required elements' supplier.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, IElementSupplier<T> supplier) {
        return findChildElements(childLoc, supplier, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param supplier Required elements' supplier.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, IElementSupplier<T> supplier,
                                                             ElementsCount count) {
        return findChildElements(childLoc, supplier, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, IElementSupplier<T> supplier,
                                                             ElementState state) {
        return findChildElements(childLoc, supplier, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of child elements.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, IElementSupplier<T> supplier,
                                                             ElementState state, ElementsCount count) {
        return findChildElements(childLoc, null, supplier, state, count);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, IElementSupplier<T> supplier) {
        return findChildElements(childLoc, name, supplier, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param count    Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, IElementSupplier<T> supplier,
                                                             ElementsCount count) {
        return findChildElements(childLoc, name, supplier, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, IElementSupplier<T> supplier,
                                                             ElementState state) {
        return findChildElements(childLoc, name, supplier, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to current form.
     *
     * @param <T>      Type of the target elements.
     * @param childLoc Locator of child elements relative to current form.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, IElementSupplier<T> supplier,
                                                             ElementState state, ElementsCount count) {
        return getFormLabel().findChildElements(childLoc, name, supplier, state, count);
    }


    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param elementType type of the element to be obtained
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, ElementType elementType) {
        return findChildElements(childLoc, elementType, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param elementType type of the element to be obtained
     * @param count       Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, ElementType elementType, ElementsCount count) {
        return findChildElements(childLoc, elementType, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param elementType type of the element to be obtained
     * @param state       Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, ElementType elementType, ElementState state) {
        return findChildElements(childLoc, elementType, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param elementType type of the element to be obtained
     * @param state       Visibility state of child elements.
     * @param count       Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, ElementType elementType, ElementState state,
                                                             ElementsCount count) {
        return findChildElements(childLoc, null, elementType, state, count);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param name        Child elements name.
     * @param elementType type of the element to be obtained
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, ElementType elementType) {
        return findChildElements(childLoc, name, elementType, ElementsCount.ANY);
    }

    /**
     * Finds displayed child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param name        Child elements name.
     * @param elementType type of the element to be obtained
     * @param count       Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, ElementType elementType,
                                                             ElementsCount count) {
        return findChildElements(childLoc, name, elementType, ElementState.DISPLAYED, count);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param name        Child elements name.
     * @param elementType type of the element to be obtained
     * @param state       Visibility state of child elements.
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, ElementType elementType,
                                                             ElementState state) {
        return findChildElements(childLoc, name, elementType, state, ElementsCount.ANY);
    }

    /**
     * Finds child elements by their locator relative to parent element.
     *
     * @param <T>         Type of the target elements.
     * @param childLoc    Locator of child elements relative to its parent.
     * @param name        Child elements name.
     * @param elementType type of the element to be obtained
     * @param state       Visibility state of target elements.
     * @param count       Expected number of elements that have to be found (zero, more then zero, any).
     * @return List of child elements.
     */
    protected <T extends IElement> List<T> findChildElements(By childLoc, String name, ElementType elementType,
                                                             ElementState state, ElementsCount count) {
        return findChildElements(childLoc, name, elementType.getClazz(), state, count);
    }
}
