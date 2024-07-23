package aquality.selenium.elements.actions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.localization.ILocalizedLogger;
import aquality.selenium.core.utilities.IElementActionRetrier;
import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput.ScrollOrigin;

import java.util.function.BiFunction;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class MouseActions {
    private final IElement element;
    private final String type;
    private final String name;
    private final ILocalizedLogger logger;
    private final IElementActionRetrier elementActionRetrier;

    public MouseActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
        this.logger = AqualityServices.getLocalizedLogger();
        this.elementActionRetrier = AqualityServices.get(IElementActionRetrier.class);
    }

    /**
     * Click via Action.
     */
    public void click() {
        logElementAction("loc.clicking");
        new JsActions(element, type).highlightElement();
        performActionAfterMove((elem, actions) -> actions.click());
    }

    /**
     * Click Right (calls context menu) on the element
     */
    public void rightClick() {
        logElementAction("loc.clicking.right");
        performActionAfterMove((elem, actions) -> actions.contextClick(elem));
    }

    /**
     * Scrolling to element
     */
    public void scrollToElement() {
        logElementAction("loc.scrolling");
        performAction((elem, actions) -> actions.scrollToElement(elem));
    }

    /**
     * Scrolling by coordinates
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public void scrollFromOrigin(int x, int y) {
        scrollFromOrigin(x, y, 0, 0);
    }

    /**
     * Scrolling by coordinates
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @param xOffset horizontal offset
     * @param yOffset vertical offset
     */
    public void scrollFromOrigin(int x, int y, int xOffset, int yOffset)
    {
        logElementAction("loc.scrolling.by", x, y);
        elementActionRetrier.doWithRetry(() -> {
            ScrollOrigin scrollOrigin = ScrollOrigin.fromElement(element.getElement(), xOffset, yOffset);
            getBrowser().scrollFromOrigin(scrollOrigin, x, y);
        });
    }

    /**
     * Move mouse to this element.
     */
    public void moveMouseToElement() {
        logElementAction("loc.moving");
        performActionAfterMove((elem, actions) -> actions);
    }

    /**
     * Move mouse from this element.
     */
    public void moveMouseFromElement() {
        logElementAction("loc.movingFrom");
        performAction(((elem, actions) -> actions.moveToElement(elem, elem.getSize().width, elem.getSize().height)));
    }

    /**
     * Performs double-click on the element.
     */
    public void doubleClick() {
        logElementAction("loc.clicking.double");
        performActionAfterMove((elem, actions) -> actions.doubleClick(elem));
    }

    private void performActionAfterMove(BiFunction<WebElement, Actions, Actions> function) {
        performAction((elem, actions) -> function.apply(elem, actions.moveToElement(elem)));
    }

    private void performAction(BiFunction<WebElement, Actions, Actions> action) {
        elementActionRetrier.doWithRetry(() -> action.apply(element.getElement(), new Actions(getBrowser().getDriver())).perform());
    }

    /**
     * The implementation of a method for logging of MouseActions
     *
     * @param key  key in localization resource of message to display in the log.
     * @param args Arguments, which will be provided to template of localized message.
     */
    private void logElementAction(String key, Object... args) {
        logger.infoElementAction(type, name, key, args);
    }
}
