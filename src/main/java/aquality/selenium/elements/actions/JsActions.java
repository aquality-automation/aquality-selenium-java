package aquality.selenium.elements.actions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.Point;

import java.util.ArrayList;

public class JsActions {

    protected IElement element;
    protected String type;
    protected String name;


    public JsActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
    }

    /**
     * Click via JS.
     */
    public void click() {
        logElementAction("loc.clicking.js");
        highlightElement();
        executeScript(JavaScript.CLICK_ELEMENT, element);
    }

    /**
     * Click on an item js click and wait for the page is loaded.
     */
    public void clickAndWait() {
        click();
        getBrowser().waitForPageToLoad();
    }

    /**
     * Highlights the element
     */
    public void highlightElement() {
        highlightElement(HighlightState.DEFAULT);
    }

    /**
     * Highlights the element.
     */
    public void highlightElement(HighlightState highlightState) {
        if (AqualityServices.getBrowserProfile().isElementHighlightEnabled() || highlightState.equals(HighlightState.HIGHLIGHT)) {
            executeScript(JavaScript.BORDER_ELEMENT, element);
        }
    }

    /**
     * Scrolling to element
     */
    public void scrollIntoView() {
        logElementAction("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT, element, true);
    }

    /**
     * Scrolling by coordinates
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        logElementAction("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_BY, element, x, y);
    }

    /**
     * Scrolling to element's center
     */
    public void scrollToTheCenter() {
        logElementAction("loc.scrolling.center.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT_CENTER, element);
    }

    /**
     * Setting value
     *
     * @param value Value
     */
    public void setValue(final String value) {
        logElementAction("loc.setting.value", value);
        executeScript(JavaScript.SET_VALUE, element, value);
    }

    /**
     * Focusing element
     */
    public void setFocus() {
        logElementAction("loc.focusing");
        executeScript(JavaScript.SET_FOCUS, element);
    }

    /**
     * Checking if element presented on screen
     *
     * @return true if is on screen, false otherwise
     */
    public boolean isElementOnScreen() {
        logElementAction("loc.is.present.js");
        return (boolean) executeScript(JavaScript.ELEMENT_IS_ON_SCREEN, element);
    }

    /**
     * Get element text
     *
     * @return element's text
     */
    public String getElementText() {
        logElementAction("loc.get.text.js");
        return (String) executeScript(JavaScript.GET_ELEMENT_TEXT, element);
    }

    /**
     * Hover mouse over element
     */
    public void hoverMouse() {
        logElementAction("loc.hover.js");
        executeScript(JavaScript.MOUSE_HOVER, element);
    }

    /**
     * Gets element coordinates relative to the View Port
     *
     * @return Point object
     */
    public Point getViewPortCoordinates() {
        ArrayList<Number> coordinates = (ArrayList<Number>) getBrowser().executeScript(JavaScript.GET_VIEWPORT_COORDINATES.getScript(), element.getElement());
        return new Point(Math.round(coordinates.get(0).floatValue()), Math.round(coordinates.get(1).floatValue()));
    }

    /**
     * Gets element's XPath
     *
     * @return element's XPath locator
     */
    public String getXPath() {
        logElementAction("loc.get.xpath.js");
        return (String) executeScript(JavaScript.GET_ELEMENT_XPATH, element);
    }

    protected Object executeScript(JavaScript javaScript, IElement element) {
        return getBrowser().executeScript(javaScript, element.getElement());
    }

    protected Object executeScript(JavaScript javaScript, IElement element, Object... args) {
        return getBrowser().executeScript(javaScript, element.getElement(), args);
    }

    /**
     * The implementation of a method for logging of Javascript actions
     *
     * @param key key in localization resource of message to display in the log.
     */
    protected void logElementAction(String key, Object... args) {
        AqualityServices.getLocalizedLogger().infoElementAction(type, name, key, args);
    }

    private Browser getBrowser() {
        return AqualityServices.getBrowser();
    }
}
