package aquality.selenium.elements.actions;

import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.browser.JavaScript;
import aquality.selenium.configuration.Configuration;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import org.openqa.selenium.Point;

import java.util.ArrayList;

public class JsActions {

    private static final Configuration configuration = Configuration.getInstance();
    private static final String LOG_DELIMITER = "::";
    private final Logger logger = Logger.getInstance();
    protected IElement element;
    private String type;
    private String name;


    public JsActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
    }

    /**
     * Click via JS.
     */
    public void click() {
        infoLoc("loc.clicking.js");
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
        if (configuration.getBrowserProfile().isElementHighlightEnabled()) {
            executeScript(JavaScript.BORDER_ELEMENT, element);
        }
    }

    /**
     * Scrolling to element
     */
    public void scrollIntoView() {
        infoLoc("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT, element, true);
    }

    /**
     * Scrolling by coordinates
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollBy(int x, int y) {
        infoLoc("loc.scrolling.js");
        executeScript(JavaScript.SCROLL_BY, element, x, y);
    }

    /**
     * Scrolling to element's center
     */
    public void scrollToTheCenter() {
        infoLoc("loc.scrolling.center.js");
        executeScript(JavaScript.SCROLL_TO_ELEMENT_CENTER, element);
    }

    /**
     * Setting value
     *
     * @param value Value
     */
    public void setValue(final String value) {
        infoLoc("loc.setting.value");
        executeScript(JavaScript.SET_VALUE, element, value);
    }

    /**
     * Focusing element
     */
    public void setFocus() {
        infoLoc("loc.focusing");
        executeScript(JavaScript.SET_FOCUS, element);
    }

    /**
     * Checking if element presented on screen
     * @return true if is on screen, false otherwise
     */
    public boolean isElementOnScreen() {
        infoLoc("loc.is.present.js");
        return (boolean) executeScript(JavaScript.ELEMENT_IS_ON_SCREEN, element);
    }

    /**
     * Get element text
     *
     * @return element's text
     */
    public String getElementText() {
        infoLoc("loc.get.text.js");
        return (String) executeScript(JavaScript.GET_ELEMENT_TEXT, element);
    }

    /**
     * Hover mouse over element
     */
    public void hoverMouse() {
        infoLoc("loc.hover.js");
        executeScript(JavaScript.MOUSE_HOVER, element);
    }

    /**
     * Gets element coordinates relative to the View Port
     * @return Point object
     */
    public Point getViewPortCoordinates() {
        ArrayList<Number> coordinates = (ArrayList<Number>) getBrowser().executeScript(JavaScript.GET_VIEWPORT_COORDINATES.getScript(), element.getElement());
        return new Point(Math.round(coordinates.get(0).floatValue()), Math.round(coordinates.get(1).floatValue()));
    }

    /**
     * Gets element's XPath
     * @return element's XPath locator
     */
    public String getXPath() {
        return (String) executeScript(JavaScript.GET_ELEMENT_XPATH, element);
    }

    protected Object executeScript(JavaScript javaScript, IElement element){
        return getBrowser().executeScript(javaScript, element.getElement());
    }

    protected Object executeScript(JavaScript javaScript, IElement element, Object... args){
        return getBrowser().executeScript(javaScript, element.getElement(), args);
    }

    /**
     * The implementation of a method for logging of Javascript actions
     *
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    private String formatJsActionMessage(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", type, name, LOG_DELIMITER, message);
    }

    protected void infoLoc(String key) {
        logger.info(formatJsActionMessage(LocalizationManager.getInstance().getValue(key)));
    }

    private Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}
