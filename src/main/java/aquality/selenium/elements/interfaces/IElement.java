package aquality.selenium.elements.interfaces;

import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.actions.JsActions;
import aquality.selenium.elements.actions.MouseActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebElement;

public interface IElement extends IParent {

    /**
     * Get clear WebElement
     *
     * @return WebElement
     */
    RemoteWebElement getElement();

    /**
     * Get clear WebElement
     *
     * @param timeout Timeout for waiting
     * @return WebElement
     */
    RemoteWebElement getElement(Long timeout);

    /**
     * Get element locator
     *
     * @return Element locator
     */
    By getLocator();

    /**
     * get element state that used for interactions
     * @return state of element that used for interactions
     */
    ElementState getElementState();

    /**
     * get element name
     * @return name
     */
    String getName();

    /**
     * Send keys.
     */
    void sendKeys(Keys key);

    /**
     * Click on the item.
     */
    void click();

    /**
     * Click on an item and wait for the page is loaded
     */
    void clickAndWait();

    /**
     * Get the item text (inner text).
     *
     * @return Text of element
     */
    String getText();

    /** return text of element with highlighting or not before getting text
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
    String getAttribute(String attr);

    /**
     * returns attribute value of element with highlighting or not before getting text
     * @param highlightState if HIGHLIGHT: create red border around element that we interact while getting text
     * @param attr html attribute name
     * @return Attribute value
     */
    String getAttribute(String attr, HighlightState highlightState);

    /**
     * Gets css value of the element.
     *
     * @param propertyName css value name
     * @return css value
     */
    String getCssValue(String propertyName);

    /**
     * Gets css value of the element.
     *
     * @param propertyName css value name
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
     * Right Click on the element
     */
    void clickRight();

    /**
     * Focuses the element
     */
    void focus();

    /**
     * Gets object for class designed to perform javascript actions
     * @return JsActions object
     */
    JsActions getJsActions();

    /**
     * Gets class designed to perform mouse actions
     * @return MouseActions class
     */
    MouseActions getMouseActions();

    /**
     * Provides ability to define of element's state (whether it is displayed, exists or not) and respective waiting functions
     * @return provider to define element's state
     */
    IElementStateProvider state();
}
