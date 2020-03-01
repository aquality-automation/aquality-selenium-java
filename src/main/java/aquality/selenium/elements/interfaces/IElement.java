package aquality.selenium.elements.interfaces;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.actions.JsActions;
import aquality.selenium.elements.actions.MouseActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public interface IElement extends aquality.selenium.core.elements.interfaces.IElement {

    /**
     * Send keys.
     *
     * @param key key for sending.
     */
    void sendKeys(Keys key);

    /**
     * Click on an item and wait for the page is loaded
     */
    void clickAndWait();

    /**
     * Get the item text (inner text).
     *
     * @return Text of element
     */
    default String getText() {
        return getText(HighlightState.DEFAULT);
    }

    /**
     * return text of element with highlighting or not before getting text
     *
     * @param highlightState if HIGHLIGHT: create red border around element that we interact while getting text
     * @return text of element
     */
    String getText(HighlightState highlightState);

    /**
     * Gets attribute value of the element.
     *
     * @param attr Attribute name
     * @return Attribute value
     */
    default String getAttribute(String attr) {
        return getAttribute(attr, HighlightState.DEFAULT);
    }

    /**
     * returns attribute value of element with highlighting or not before getting text
     *
     * @param highlightState if HIGHLIGHT: create red border around element that we interact while getting text
     * @param attr           html attribute name
     * @return Attribute value
     */
    String getAttribute(String attr, HighlightState highlightState);

    /**
     * Gets css value of the element.
     *
     * @param propertyName css value name
     * @return css value
     */
    default String getCssValue(String propertyName) {
        return getCssValue(propertyName, HighlightState.DEFAULT);
    }

    /**
     * Gets css value of the element.
     *
     * @param propertyName   css value name
     * @param highlightState if HIGHLIGHT: create red border around element that we interact while getting css value
     * @return css value
     */
    String getCssValue(String propertyName, HighlightState highlightState);

    /**
     * set innerHtml via javascript <b>arguments[0].innerHTML='%1$s' </b>.
     *
     * @param value value
     */
    void setInnerHtml(String value);

    /**
     * Focuses the element
     */
    default void focus() {
        getJsActions().setFocus();
    }

    /**
     * Gets object for class designed to perform javascript actions
     *
     * @return JsActions object
     */
    JsActions getJsActions();

    /**
     * Gets class designed to perform mouse actions
     *
     * @return MouseActions class
     */
    MouseActions getMouseActions();

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
    <T extends IElement> T findChildElement(By childLoc, String name, ElementType elementType, ElementState state);

    /**
     * Find an element in the parent element with default name.
     *
     * @param childLoc    child element locator
     * @param elementType type of the element to be obtained
     * @param state       visibility state of target element
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    default <T extends IElement> T findChildElement(By childLoc, ElementType elementType, ElementState state) {
        return findChildElement(childLoc, null, elementType, state);
    }

    /**
     * Find an element in the parent element with DISPLAYED state and default name.
     *
     * @param childLoc    child element locator
     * @param elementType type of the element to be obtained
     * @param <T>         type of the element to be obtained
     * @return found child element
     */
    default <T extends IElement> T findChildElement(By childLoc, ElementType elementType) {
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
    default <T extends IElement> T findChildElement(By childLoc, String name, ElementType elementType) {
        return findChildElement(childLoc, name, elementType, ElementState.DISPLAYED);
    }
}
