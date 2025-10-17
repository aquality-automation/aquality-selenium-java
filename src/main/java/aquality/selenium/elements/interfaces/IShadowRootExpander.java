package aquality.selenium.elements.interfaces;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.core.elements.RelativeElementFinder;
import aquality.selenium.core.elements.interfaces.IElementFinder;
import aquality.selenium.core.elements.interfaces.IElementSupplier;
import aquality.selenium.core.localization.ILocalizationManager;
import aquality.selenium.elements.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.util.List;

import static aquality.selenium.browser.AqualityServices.getConditionalWait;
import static aquality.selenium.browser.AqualityServices.getLocalizedLogger;

public interface IShadowRootExpander {
    /**
     * Expands shadow root.
     *
     * @return ShadowRoot search context.
     */
    SearchContext expandShadowRoot();

    /**
     * Provides {@link IElementFactory} to find elements in the shadow root of the current element.
     *
     * @return instance of ElementFactory for the shadow root.
     */
    default IElementFactory getShadowRootElementFactory() {
        IElementFinder elementFinder = new RelativeElementFinder(getLocalizedLogger(), getConditionalWait(), this::expandShadowRoot);
        return new ElementFactory(getConditionalWait(), elementFinder, AqualityServices.get(ILocalizationManager.class));
    }

    /**
     * Finds an element in the shadow root of the current element.
     *
     * @param locator shadowed element locator
     * @param name    output name in logs
     * @param clazz   class or interface of the element to be obtained
     * @param state   visibility state of target element
     * @param <T>     the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, String name, Class<T> clazz, ElementState state) {
        return getShadowRootElementFactory().getCustomElement(clazz, locator, name, state);
    }

    /**
     * Finds an element in the shadow root of the current element with DISPLAYED state
     *
     * @param locator shadowed element locator
     * @param name    output name in logs
     * @param clazz   class or interface of the element to be obtained
     * @param <T>     the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, String name, Class<T> clazz) {
        return findElementInShadowRoot(locator, name, clazz, ElementState.DISPLAYED);
    }

    /**
     * Finds an element in the shadow root of the current element.
     *
     * @param locator shadowed element locator
     * @param clazz   class or interface of the element to be obtained
     * @param state   visibility state of target element
     * @param <T>     the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, Class<T> clazz, ElementState state) {
        return findElementInShadowRoot(locator, null, clazz, state);
    }

    /**
     * Finds an element in the shadow root of the current element with DISPLAYED state
     *
     * @param locator shadowed element locator
     * @param clazz   class or interface of the element to be obtained
     * @param <T>     the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, Class<T> clazz) {
        return findElementInShadowRoot(locator, null, clazz, ElementState.DISPLAYED);
    }

    /**
     * Finds an element in the shadow root of the current element.
     *
     * @param locator  Child element locator
     * @param name     output name in logs
     * @param supplier required element's supplier
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, String name, IElementSupplier<T> supplier, ElementState state) {
        return getShadowRootElementFactory().getCustomElement(supplier, locator, name, state);
    }

    /**
     * Finds an element in the shadow root of the current element with DISPLAYED state
     *
     * @param locator  shadowed element locator
     * @param name     output name in logs
     * @param supplier required element's supplier
     * @param <T>      the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, String name, IElementSupplier<T> supplier) {
        return findElementInShadowRoot(locator, name, supplier, ElementState.DISPLAYED);
    }

    /**
     * Finds an element in the shadow root of the current element.
     *
     * @param locator  shadowed element locator
     * @param supplier required element's supplier
     * @param state    visibility state of target element
     * @param <T>      the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, IElementSupplier<T> supplier, ElementState state) {
        return findElementInShadowRoot(locator, null, supplier, state);
    }

    /**
     * Finds an element in the shadow root of the current element with DISPLAYED state
     *
     * @param locator  shadowed element locator
     * @param supplier required element's supplier
     * @param <T>      the type of the element to be obtained
     * @return found shadowed element
     */
    default <T extends IElement> T findElementInShadowRoot(By locator, IElementSupplier<T> supplier) {
        return findElementInShadowRoot(locator, null, supplier, ElementState.DISPLAYED);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, Class<T> clazz) {
        return findElementsInShadowRoot(locator, clazz, ElementsCount.ANY);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param count   Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, Class<T> clazz, ElementsCount count) {
        return findElementsInShadowRoot(locator, clazz, ElementState.DISPLAYED, count);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param state   Visibility state of shadowed elements.
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, Class<T> clazz, ElementState state) {
        return findElementsInShadowRoot(locator, clazz, state, ElementsCount.ANY);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param state   Visibility state of shadowed elements.
     * @param count   Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, Class<T> clazz, ElementState state, ElementsCount count) {
        return findElementsInShadowRoot(locator, null, clazz, state, count);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param name    Child elements name.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, Class<T> clazz) {
        return findElementsInShadowRoot(locator, name, clazz, ElementsCount.ANY);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param name    Child elements name.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param count   Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, Class<T> clazz, ElementsCount count) {
        return findElementsInShadowRoot(locator, name, clazz, ElementState.DISPLAYED, count);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param name    Child elements name.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param state   Visibility state of shadowed elements.
     * @param <T>     Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, Class<T> clazz, ElementState state) {
        return findElementsInShadowRoot(locator, name, clazz, state, ElementsCount.ANY);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param <T>     Type of the target elements.
     * @param locator Locator of shadowed elements relative to shadow root.
     * @param name    Child elements name.
     * @param clazz   Class or interface of the elements to be obtained.
     * @param state   Visibility state of target elements.
     * @param count   Expected number of elements that have to be found (zero, more than zero, any).
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, Class<T> clazz, ElementState state, ElementsCount count) {
        return getShadowRootElementFactory().findElements(locator, name, clazz, count, state);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param supplier Required elements' supplier.
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, IElementSupplier<T> supplier) {
        return findElementsInShadowRoot(locator, supplier, ElementsCount.ANY);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param supplier Required elements' supplier.
     * @param count    Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, IElementSupplier<T> supplier, ElementsCount count) {
        return findElementsInShadowRoot(locator, supplier, ElementState.DISPLAYED, count);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of shadowed elements.
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, IElementSupplier<T> supplier, ElementState state) {
        return findElementsInShadowRoot(locator, supplier, state, ElementsCount.ANY);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of shadowed elements.
     * @param count    Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, IElementSupplier<T> supplier, ElementState state, ElementsCount count) {
        return findElementsInShadowRoot(locator, null, supplier, state, count);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, IElementSupplier<T> supplier) {
        return findElementsInShadowRoot(locator, name, supplier, ElementsCount.ANY);
    }

    /**
     * Finds displayed shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param count    Expected number of elements that have to be found (zero, more than zero, any).
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, IElementSupplier<T> supplier, ElementsCount count) {
        return findElementsInShadowRoot(locator, name, supplier, ElementState.DISPLAYED, count);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of shadowed elements.
     * @param <T>      Type of the target elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, IElementSupplier<T> supplier, ElementState state) {
        return findElementsInShadowRoot(locator, name, supplier, state, ElementsCount.ANY);
    }

    /**
     * Finds shadowed elements by their locator relative to shadow root of the current element.
     *
     * @param <T>      Type of the target elements.
     * @param locator  Locator of shadowed elements relative to shadow root.
     * @param name     Child elements name.
     * @param supplier Required elements' supplier.
     * @param state    Visibility state of shadowed elements.
     * @return List of shadowed elements.
     */
    default <T extends IElement> List<T> findElementsInShadowRoot(By locator, String name, IElementSupplier<T> supplier, ElementState state, ElementsCount count) {
        return getShadowRootElementFactory().findElements(locator, name, supplier, count, state);
    }
}
