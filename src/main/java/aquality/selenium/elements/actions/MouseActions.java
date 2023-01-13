package aquality.selenium.elements.actions;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.IElementActionRetrier;
import aquality.selenium.elements.interfaces.IElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.UnaryOperator;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class MouseActions {
    private final IElement element;
    private final String type;
    private final String name;

    public MouseActions(IElement element, String type) {
        this.element = element;
        this.type = type;
        this.name = element.getName();
    }

    /**
     * Click via Action.
     */
    public void click() {
        infoLoc("loc.clicking");
        new JsActions(element, type).highlightElement();
        performAction(Actions::click);
    }

    /**
     * Click Right (calls context menu) on the element
     */
    public void rightClick() {
        infoLoc("loc.clicking.right");
        performAction(actions -> actions.contextClick(element.getElement()));
    }

    /**
     * Move mouse to this element.
     */
    public void moveMouseToElement() {
        infoLoc("loc.moving");
        performAction(actions -> actions);
    }

    /**
     * Move mouse from this element.
     */
    public void moveMouseFromElement() {
        infoLoc("loc.movingFrom");
        AqualityServices.get(IElementActionRetrier.class).doWithRetry(() ->
                new Actions(getBrowser().getDriver())
                        .moveToElement(element.getElement(), -element.getElement().getSize().width, -element.getElement().getSize().height)
                        .build().perform());
    }

    /**
     * Performs double-click on the element.
     */
    public void doubleClick() {
        infoLoc("loc.clicking.double");
        performAction(actions -> actions.doubleClick(element.getElement()));
    }

    private void performAction(UnaryOperator<Actions> function) {
        Actions actions = new Actions(getBrowser().getDriver()).moveToElement(element.getElement());
        AqualityServices.get(IElementActionRetrier.class).doWithRetry(() ->
                function.apply(actions).build().perform());
    }

    /**
     * The implementation of a method for logging of MouseActions
     *
     * @param key key in localization resource of message to display in the log.
     */
    private void infoLoc(String key) {
        AqualityServices.getLocalizedLogger().infoElementAction(type, name, key);
    }
}
