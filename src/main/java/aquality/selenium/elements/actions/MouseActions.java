package aquality.selenium.elements.actions;


import aquality.selenium.browser.Browser;
import aquality.selenium.browser.BrowserManager;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.localization.LocalizationManager;
import aquality.selenium.logger.Logger;
import aquality.selenium.utils.ElementActionRetrier;
import org.openqa.selenium.interactions.Actions;

import java.util.function.Function;

public class MouseActions {

    private static final String LOG_DELIMITER = "::";
    private final Logger logger = Logger.getInstance();
    private IElement element;
    private String type;
    private String name;


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
        performAction(actions -> actions.click());
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
        element.getElement().getCoordinates().inViewPort();
        performAction(actions -> actions.moveToElement(element.getElement()));
    }

    /**
     * Move mouse from this element.
     */
    public void moveMouseFromElement() {
        infoLoc("loc.movingFrom");
        performAction(actions -> actions.moveToElement(element.getElement(),
                -element.getElement().getSize().width / 2, -element.getElement().getSize().height / 2));
    }

    /**
     * Double click on the item. Waiting for the end of renderiga
     */
    public void doubleClick() {
        infoLoc("loc.clicking.double");
        ElementActionRetrier.doWithRetry(() -> (getBrowser().getDriver()).getMouse().mouseMove(element.getElement().getCoordinates()));
        performAction(actions -> actions.doubleClick(element.getElement()));
    }

    private void performAction(Function<Actions, Actions> function) {
        Actions actions = new Actions(getBrowser().getDriver()).moveToElement(element.getElement());
        ElementActionRetrier.doWithRetry(() ->
                        function.apply(actions).build().perform());
    }

    /**
     * The implementation of a method for logging of MouseActions
     *
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    private String formatActionMessage(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", type, name, LOG_DELIMITER, message);
    }

    private void infoLoc(String key) {
        logger.info(formatActionMessage(LocalizationManager.getInstance().getValue(key)));
    }

    private Browser getBrowser(){
        return BrowserManager.getBrowser();
    }
}
