package aquality.selenium.elements.actions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.core.utilities.IElementActionRetrier;
import aquality.selenium.elements.HighlightState;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.IShadowRootExpander;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.SearchContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsActions implements IShadowRootExpander {

    protected IElement element;
    protected String type;
    protected String name;

    public JsActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
    }

    @Override
    public SearchContext expandShadowRoot() {
        logElementAction("loc.shadowroot.expand.js");
        return (SearchContext) executeScript(JavaScript.EXPAND_SHADOW_ROOT);
    }

    /**
     * Setting attribute value.
     * @param name Attribute name
     * @param value Value to set
     */
    public void setAttribute(String name, String value) {
        logElementAction("loc.el.attr.set", name, value);
        executeScript(JavaScript.SET_ATTRIBUTE, name, value);
    }

    /**
     * Click via JS.
     */
    public void click() {
        logElementAction("loc.clicking.js");
        highlightElement();
        executeScript(JavaScript.CLICK_ELEMENT);
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
            executeScript(JavaScript.BORDER_ELEMENT);
        }
    }

    /**
     * Scrolling to element
     */
    public void scrollIntoView() {
        logElementAction("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT, true);
    }

    /**
     * Scrolling by coordinates
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        logElementAction("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_BY, x, y);
    }

    /**
     * Scrolling to element's center
     */
    public void scrollToTheCenter() {
        logElementAction("loc.scrolling.center.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT_CENTER);
    }

    /**
     * Setting value
     *
     * @param value Value
     */
    public void setValue(final String value) {
        logElementAction("loc.setting.value", value);
        executeScript(JavaScript.SET_VALUE, value);
    }

    /**
     * Focusing element
     */
    public void setFocus() {
        logElementAction("loc.focusing");
        executeScript(JavaScript.SET_FOCUS);
    }

    /**
     * Checking if element presented on screen
     *
     * @return true if is on screen, false otherwise
     */
    public boolean isElementOnScreen() {
        logElementAction("loc.is.present.js");
        boolean value = (boolean) executeScript(JavaScript.ELEMENT_IS_ON_SCREEN);
        logElementAction("loc.is.present.value", value);
        return value;
    }

    /**
     * Get element text
     *
     * @return element's text
     */
    public String getElementText() {
        logElementAction("loc.get.text.js");
        String value = (String) executeScript(JavaScript.GET_ELEMENT_TEXT);
        logElementAction("loc.text.value", value);
        return value;
    }

    /**
     * Hover mouse over element
     */
    public void hoverMouse() {
        logElementAction("loc.hover.js");
        executeScript(JavaScript.MOUSE_HOVER);
    }

    /**
     * Gets element coordinates relative to the View Port
     *
     * @return Point object
     */
    @SuppressWarnings("unchecked")
    public Point getViewPortCoordinates() {
        List<Number> coordinates = (ArrayList<Number>) executeScript(JavaScript.GET_VIEWPORT_COORDINATES);
        return new Point(Math.round(coordinates.get(0).floatValue()), Math.round(coordinates.get(1).floatValue()));
    }

    /**
     * Gets element's XPath
     *
     * @return element's XPath locator
     */
    public String getXPath() {
        logElementAction("loc.get.xpath.js");
        String value = (String) executeScript(JavaScript.GET_ELEMENT_XPATH);
        logElementAction("loc.xpath.value", value);
        return value;
    }

    private Object[] resolveArguments(Object... args) {
        List<Object> arguments = new ArrayList<>();
        arguments.add(element.getElement());
        arguments.addAll(Arrays.asList(args));
        return arguments.toArray();
    }

    /**
     * Executes pinned JavaScript against the element and gets result value.
     * @param pinnedScript Instance of script pinned with {@link Browser#javaScriptEngine()}
     * @return Script execution result.
     */
    public Object executeScript(ScriptKey pinnedScript, Object... args) {
        logElementAction("loc.el.execute.pinnedjs");
        Object result = getActionRetrier().doWithRetry(() -> getBrowser().executeScript(pinnedScript, resolveArguments(args)));
        if (result != null) {
            logElementAction("loc.el.execute.pinnedjs.result", result);
        }
        return result;
    }

    protected Object executeScript(JavaScript javaScript) {
        return getActionRetrier().doWithRetry(() -> getBrowser().executeScript(javaScript, element.getElement()));
    }

    protected Object executeScript(JavaScript javaScript, Object... args) {
        return getActionRetrier().doWithRetry(() -> getBrowser().executeScript(javaScript, resolveArguments(args)));
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

    private IElementActionRetrier getActionRetrier() {
        return AqualityServices.get(IElementActionRetrier.class);
    }
}
